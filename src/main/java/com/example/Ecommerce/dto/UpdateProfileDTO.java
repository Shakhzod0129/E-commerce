package com.example.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProfileDTO {
    private String name;
    private String surname;
    private String email;

}
