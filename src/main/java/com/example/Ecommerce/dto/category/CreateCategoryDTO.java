package com.example.Ecommerce.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCategoryDTO {

    @NotBlank(message = "Category name may not be blank")
    @Size(max = 255, message = "Category name length must be less than or equal to 255 characters")
    private String name;

    private Long parentId;


}
