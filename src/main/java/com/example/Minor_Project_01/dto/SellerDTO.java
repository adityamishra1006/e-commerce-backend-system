package com.example.Minor_Project_01.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
    private String name;
    private String email;
    private Long companyId;
}
