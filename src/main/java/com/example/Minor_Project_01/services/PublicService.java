package com.example.Minor_Project_01.services;


import com.example.Minor_Project_01.dto.PasswordReqDTO;
import com.example.Minor_Project_01.dto.SignUpResponseDTO;
import com.example.Minor_Project_01.dto.UserDTO;
import com.example.Minor_Project_01.entity.Role;
import com.example.Minor_Project_01.entity.User;
import com.example.Minor_Project_01.exception.NotFoundException;
import com.example.Minor_Project_01.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PublicService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public SignUpResponseDTO createUser(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(Role.CUSTOMER);

        userRepo.save(user);

        String token = UUID.randomUUID().toString();
        String key = "password: " + token;
        String value = userDTO.getEmail();

        redisTemplate.opsForValue().set(key, value, 2, TimeUnit.MINUTES);

        // Trigger Password Rest and Email Varification

        String resetURL = "http://localhost:8080/password-reset/" + token;

        SignUpResponseDTO responseDTO = new SignUpResponseDTO();
        responseDTO.setMessage("User Created");
        responseDTO.setLink(resetURL);

        return responseDTO;
    }

    public String resetPassword(PasswordReqDTO passwordReqDTO, String token) throws NotFoundException {
        String key = "password: " + token;
        String value = redisTemplate.opsForValue().get(key);
        if(value == null || !value.equals(passwordReqDTO.getEmail())){
            throw new NotFoundException("Invalid Token");
        }

        User user = userRepo.findByEmail(passwordReqDTO.getEmail());
        user.setPassword(passwordEncoder.encode(passwordReqDTO.getNewPassword()));
        userRepo.save(user);

        return "Password reset successfully";
    }
}
