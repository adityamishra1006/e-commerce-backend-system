package com.example.Minor_Project_01.controller;

import com.example.Minor_Project_01.dto.AddToOrderDTO;
import com.example.Minor_Project_01.dto.OrderDetailDTO;
import com.example.Minor_Project_01.dto.ProductDTO;
import com.example.Minor_Project_01.dto.ResponseDTO;
import com.example.Minor_Project_01.exception.NotFoundException;
import com.example.Minor_Project_01.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;



    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getProduct(id));
    }

    @PostMapping("/order-item")
    public ResponseEntity<OrderDetailDTO> addToOrder(@RequestBody AddToOrderDTO addToOrderDto) throws NotFoundException {
        OrderDetailDTO response = customerService.addToOrder(addToOrderDto);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/orders")
//    public ResponseEntity<List<OrderDetailDTO>> getAllOrders(){
//
//    }

    @PutMapping("/orders/{orderId}/submit")
    public ResponseEntity<ResponseDTO> submitOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(customerService.submitOrder(orderId));
    }

//    @PutMapping("/orders/{orderId}/accept")
//    public ResponseEntity<ResponseDTO> acceptOrder(@PathVariable Long orderId){
//
//    }



}
