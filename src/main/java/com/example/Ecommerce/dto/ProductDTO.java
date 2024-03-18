package com.example.Ecommerce.dto;

import com.example.Ecommerce.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Long id;
    private Long storeId;
    private List<Long> categoryId;
    private List<String> attachId;
    private Double price;
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private ProductStatus status;
    private Double rate;
    private Long countComments;
    private Long countOrders;
}
