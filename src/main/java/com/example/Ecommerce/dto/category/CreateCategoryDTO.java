package com.example.Ecommerce.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCategoryDTO {


    private String name;
    @NotBlank(message = "Category name may not be blank")
    @Size(max = 255, message = "Category name length must be less than or equal to 255 characters")
    private String nameUz;
    @NotBlank(message = "Category name may not be blank")
    @Size(max = 255, message = "Category name length must be less than or equal to 255 characters")
    private String nameRu;
    @NotBlank(message = "Category name may not be blank")
    @Size(max = 255, message = "Category name length must be less than or equal to 255 characters")
    private String nameEn;

    @NotNull(message = "Order number may not be null")
    @Positive(message = "Order number must be positive")
    private Integer orderNumber;

    private Long parentId;


}
