package com.example.Ecommerce.dto.cart;

import com.example.Ecommerce.dto.product.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDTO {
    private Long id;
    private Long profileId;
    private Map<Long,Integer> productIdAndItsQuantity;
    @Size(min = 1, message = "At least one product ID must be provided")
    private List<Long> productIds;
    private Double totalPrice;
    private Integer quantityOfProducts;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private ProductDTO product;
}
