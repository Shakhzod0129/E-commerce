package com.example.Ecommerce.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductListDTO {
    private Long productId;
    private Integer quantity;
}
