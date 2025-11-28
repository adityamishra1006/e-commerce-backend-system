package com.example.Minor_Project_01.config;

import com.example.Minor_Project_01.dto.ResponseDTO;
import com.example.Minor_Project_01.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO> handleNotFoundException(NotFoundException ex){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage(ex.getMessage());
        responseDTO.setStatusCode("404-NOT-FOUND");
        return ResponseEntity.badRequest().body(responseDTO);
    }
}
