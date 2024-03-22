package com.example.Ecommerce.dto.product;

import com.example.Ecommerce.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateProductDTO {
    @NotNull(message = "Store ID may not be null")
    private Long storeId;

    @NotNull(message = "Category IDs may not be null")
    @Size(min = 1, message = "At least one category ID must be provided")
    private List<Long> categoryId;

    @NotNull(message = "Attach IDs may not be null")
    @Size(min = 1, message = "At least one attach ID must be provided")
    private List<String> attachId;

    @NotNull(message = "Price may not be null")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotBlank(message = "Name in Uzbek may not be blank")
    private String nameUz;

    @NotBlank(message = "Name in Russian may not be blank")
    private String nameRu;

    @NotBlank(message = "Name in English may not be blank")
    private String nameEn;

    @NotBlank(message = "Description may not be blank")
    private String description;

    @NotBlank(message = "Description in Uzbek may not be blank")
    private String descriptionUz;

    @NotBlank(message = "Description in Russian may not be blank")
    private String descriptionRu;

    @NotBlank(message = "Description in English may not be blank")
    private String descriptionEn;

    // Getter va Setter metodi


}
