package com.example.Ecommerce.dto.category;

import com.example.Ecommerce.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Long id;
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
    private Long parentId;
    private Status status;
    private Integer orderNumber;
    private LocalDateTime createdDate;
}
