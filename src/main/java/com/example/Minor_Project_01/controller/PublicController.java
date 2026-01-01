package com.example.Minor_Project_01.controller;

import com.example.Minor_Project_01.dto.PasswordReqDTO;
import com.example.Minor_Project_01.dto.ProductDTO;
import com.example.Minor_Project_01.dto.SignUpResponseDTO;
import com.example.Minor_Project_01.dto.UserDTO;
import com.example.Minor_Project_01.exception.NotFoundException;
import com.example.Minor_Project_01.services.CustomerService;
import com.example.Minor_Project_01.services.PublicService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private PublicService publicService;

    @GetMapping("products")
    public ResponseEntity<List<ProductDTO>> getProductsByKey(@RequestParam String keyword, @RequestParam Integer pageSize, @RequestParam Integer pageNo){
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        return ResponseEntity.ok(customerService.getProductsByKeyword(keyword, pageable));
    }

    @PostMapping("/signup")
    ResponseEntity<SignUpResponseDTO> createCustomerUser(@RequestBody UserDTO userDTO){
        SignUpResponseDTO responseDTO = publicService.createUser(userDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/password-reset/{token}")
    ResponseEntity<String> resetPassword(@RequestBody PasswordReqDTO passwordReqDTO, @PathVariable String token) throws NotFoundException {
        String response = publicService.resetPassword(passwordReqDTO, token);
        return ResponseEntity.ok(response);
    }

}
