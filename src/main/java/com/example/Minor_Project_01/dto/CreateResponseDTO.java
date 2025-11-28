package com.example.Minor_Project_01.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateResponseDTO {
    private Long id;
    private String errorCode;
    private String message;
}
