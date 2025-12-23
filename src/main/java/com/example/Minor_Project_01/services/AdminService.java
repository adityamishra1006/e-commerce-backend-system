package com.example.Minor_Project_01.services;

import com.example.Minor_Project_01.dto.CreateCompanyRequestDTO;
import com.example.Minor_Project_01.dto.CreateResponseDTO;
import com.example.Minor_Project_01.dto.ResponseDTO;
import com.example.Minor_Project_01.dto.SellerDTO;
import com.example.Minor_Project_01.entity.Company;
import com.example.Minor_Project_01.entity.Role;
import com.example.Minor_Project_01.entity.User;
import com.example.Minor_Project_01.exception.NotFoundException;
import com.example.Minor_Project_01.repo.CompanyRepo;
import com.example.Minor_Project_01.repo.UserRepo;
import jakarta.persistence.EntityManager;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public CreateResponseDTO createCompany(CreateCompanyRequestDTO companyRequestDTO){
        Company company = new Company();
        company.setName(companyRequestDTO.getName());
        company.setActive(true);

        entityManager.persist(company);

        CreateResponseDTO response = new CreateResponseDTO();
        response.setId(company.getId());
        return response;
    }

    @Transactional
    public CreateResponseDTO createSeller(SellerDTO sellerDTO){
        Company company = companyRepo.findById(sellerDTO.getCompanyId()).get();

        if(company == null){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Company not found with id: " + sellerDTO.getCompanyId());
        }

        User seller = new User();
        seller.setName(sellerDTO.getName());
        seller.setCompany(company);
        seller.setEmail(sellerDTO.getEmail());
        seller.setRole(Role.SELLER);
        seller.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));

        entityManager.persist(seller);

        CreateResponseDTO response = new CreateResponseDTO();
        response.setId(seller.getId());
        return response;
    }


    public List<SellerDTO> getAllSellers(){
        List<User> userList = userRepo.findByRole(Role.SELLER);
        List<SellerDTO> result = new ArrayList<>();
        for(User user : userList){
            SellerDTO sellerDTO = SellerDTO.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .companyId(user.getCompany().getId())
                    .build();
            result.add(sellerDTO);
        }
        return result;
    }

    public ResponseDTO deleteSeller(Long id) throws NotFoundException {
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("Seller not found with id: " + id));
        userRepo.deleteById(id);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Seller deleted successfully");
        responseDTO.setStatusCode("555-055-DEL");
        return responseDTO;
    }

}
