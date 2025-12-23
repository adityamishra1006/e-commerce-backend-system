package com.example.Minor_Project_01.services;

import com.example.Minor_Project_01.dto.*;
import com.example.Minor_Project_01.entity.*;
import com.example.Minor_Project_01.exception.NotFoundException;
import com.example.Minor_Project_01.repo.OrderRepo;
import com.example.Minor_Project_01.repo.ProductRepo;
import com.example.Minor_Project_01.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    public List<ProductDTO> getProductsByKeyword(String keyword, Pageable pageable){
        List<Product> productList = productRepo.findByNameContaining(keyword, pageable);
        List<ProductDTO> result = new ArrayList<>();
        for(Product product : productList){
            ProductDTO productDTO = ProductDTO.buildDTOFromProdcut(product);
            result.add(productDTO);
        }
        return result;
    }

    public ProductDTO getProduct(Long id){
        Product product = productRepo.findById(id).get();
        ProductDTO productDTO = ProductDTO.buildDTOFromProdcut(product);
        return productDTO;
    }

    @Transactional
    public OrderDetailDTO addToOrder(AddToOrderDTO addToOrderDto) throws NotFoundException {
        Product product = productRepo.findById(addToOrderDto.getProductId()).orElseThrow(() -> new NotFoundException("Product not found with id: " + addToOrderDto.getProductId()));

        //if product is null
//        if(product == null){
//            // throw exception
//            throw new NotFoundException("Product not found with id: " + addToOrderDto.getProductId());
//        }
//
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orderList = orderRepo.findByStatusAndUser(OrderStatus.DRAFT, user);

        Order existingOrder;
        if(!orderList.isEmpty()){
            existingOrder = orderList.get(0);
        } else {
            existingOrder = Order.builder()
                    .status(OrderStatus.DRAFT)
                    .totalAmount(0.0)
                    .user(user)
                    .orderItems(new ArrayList<>())
                    .build();
        }
        OrderItem orderItem = OrderItem.builder()
                .order(existingOrder)
                .price(product.getPrice())
                .quantity(addToOrderDto.getQuantity())
                .product(product)
                .build();
        existingOrder.getOrderItems().add(orderItem);

        if(product.getStock() < orderItem.getQuantity()){
            // throw exception
        }

        double totalItemPrice = product.getPrice() * orderItem.getQuantity();
        existingOrder.setTotalAmount(existingOrder.getTotalAmount() + totalItemPrice);
        existingOrder = orderRepo.save(existingOrder);

        product.setStock(product.getStock() - orderItem.getQuantity());

        OrderDetailDTO orderDetailDto = new OrderDetailDTO();
        orderDetailDto.setOrderId(existingOrder.getId());
        orderDetailDto.setOrderTotalPrice(existingOrder.getTotalAmount());
        List<OrderItemDTO> orderItemDtoList = new ArrayList<>();
        for(OrderItem orderItem1 : existingOrder.getOrderItems()){
            orderItemDtoList.add(OrderItemDTO.mapOrderItemToDto(orderItem1));
        }
        orderDetailDto.setOrderItems(orderItemDtoList);
        return orderDetailDto;
    }

    public ResponseDTO submitOrder(Long orderId){
        Order order = orderRepo.findById(orderId).get();
        ResponseDTO responseDto = new ResponseDTO();
        if(order.getStatus() == OrderStatus.DRAFT){
            order.setStatus(OrderStatus.PLACED);
            orderRepo.save(order);
            responseDto.setMessage("Order submitted successfully");
            responseDto.setStatusCode("555-055-SUB");
            /*
            Trigger an email to the user confirming the order placement.
            Trigger an email to the admin/seller notifying them of the new order. Seller accept the order.
             */
        }
        else{
            responseDto.setMessage("Your cart is empty. Please add items to your cart before submitting the order.");
            responseDto.setStatusCode("555-055-FSB");
        }
        return responseDto;
    }
}
