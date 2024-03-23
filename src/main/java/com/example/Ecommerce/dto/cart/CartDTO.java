package com.example.Ecommerce.dto.cart;

import com.example.Ecommerce.dto.product.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDTO {
    private Long id;
    private Long profileId;
    private List<Long> productId;
    private Double totalPrice;
    private Integer quantityOfProducts;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private ProductDTO product;
}
