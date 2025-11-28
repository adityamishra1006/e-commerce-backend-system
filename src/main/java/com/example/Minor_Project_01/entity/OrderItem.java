package com.example.Minor_Project_01.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    private Double price;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
