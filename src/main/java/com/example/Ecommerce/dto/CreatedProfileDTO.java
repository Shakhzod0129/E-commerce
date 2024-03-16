package com.example.Ecommerce.dto;

import com.example.Ecommerce.enums.Gender;
import com.example.Ecommerce.enums.ProfileRole;
import com.example.Ecommerce.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatedProfileDTO {

    @NotBlank(message = "Profile name required")
    private String name;
    @NotBlank(message = "Profile surname required")
    private String surname;
    @NotBlank(message = "Profile email required")
    @Email(message = "Email should be valid", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotBlank(message = "Profile password required")
    private String password;
    private String attachId;
    @NotNull(message = "Profile role required")
    private ProfileRole role;
    private String phoneNumber;
    private Gender gender;
}
