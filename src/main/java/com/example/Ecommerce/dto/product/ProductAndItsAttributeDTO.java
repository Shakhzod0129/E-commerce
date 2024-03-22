package com.example.Ecommerce.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Qiymat (uzbek) maydoni bo'sh bo'lishi mumkin emas")
    private String valueUz;

    @NotBlank(message = "Qiymat (ruscha) maydoni bo'sh bo'lishi mumkin emas")
    private String valueRu;

    @NotBlank(message = "Qiymat (inglizcha) maydoni bo'sh bo'lishi mumkin emas")
    private String valueEn;
}
