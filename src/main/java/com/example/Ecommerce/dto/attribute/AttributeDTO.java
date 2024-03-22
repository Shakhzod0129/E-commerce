package com.example.Ecommerce.dto.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeDTO {
    private Long id;
    private String nameUz;
    private String nameRu;
    private String nameEn;}
