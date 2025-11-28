package com.example.Minor_Project_01.dto;

import com.example.Minor_Project_01.entity.OrderItem;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    private Long id;
    private Integer quantity;
    private Double price;
    private String productName;
    private Double totalPrice;

    public static OrderItemDTO mapOrderItemToDto(OrderItem orderItem){
        OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                .id(orderItem.getId())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getPrice() * orderItem.getQuantity())
                .productName(orderItem.getProduct().getName())
                .build();
        return orderItemDTO;
    }
}
