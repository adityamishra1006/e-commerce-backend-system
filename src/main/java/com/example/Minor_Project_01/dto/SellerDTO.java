package com.example.Minor_Project_01.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
    @NotNull
    @Size(min=3, max=20)
    private String name;
    @NotNull
    private String email;
    @NotNull
    private Long companyId;
}
