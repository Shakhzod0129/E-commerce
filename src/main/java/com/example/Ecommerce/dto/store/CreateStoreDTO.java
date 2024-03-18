package com.example.Ecommerce.dto.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateStoreDTO {
    @NotBlank(message = "Category name may not be blank")
    @Size(max = 255, message = "Store name length must be less than or equal to 255 characters")
    private String name;
    @NotBlank(message = "Profile id must not be null")
    private Long profileId;
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 100, message = "Description must be between 10 and 100 characters long")
    private String description;
    private String attachId;

}
