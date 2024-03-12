package com.example.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAndItsAttributeDTO {
    private Long id;
    private Long productId;
    private Long attributeId;
    private String value;
}
