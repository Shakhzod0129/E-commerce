package com.example.Ecommerce.dto.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateAttributeDTO {
    @NotBlank(message = "Attribute name may not be blank")
    @Size(max = 255, message = "Attribute name length must be less than or equal to 255 characters")
    private String nameUz;
    @NotBlank(message = "Attribute name may not be blank")
    @Size(max = 255, message = "Attribute name length must be less than or equal to 255 characters")
    private String nameRu;
    @NotBlank(message = "Attribute name may not be blank")
    @Size(max = 255, message = "Attribute name length must be less than or equal to 255 characters")
    private String nameEn;

}
