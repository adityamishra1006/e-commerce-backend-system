package com.example.Minor_Project_01.dto;


import com.example.Minor_Project_01.entity.Product;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;
    private String description;

    private Double price;
    private Integer stock;
    private Boolean active;

    private String imageUrl;
    private Long categoryId;

    public static ProductDTO buildDTOFromProdcut(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice())
                .stock(product.getStock())
                .active(product.getActive())
                .categoryId(product.getCategory().getId())
                .description(product.getDescription())
                .build();
        return productDTO;
    }
}
