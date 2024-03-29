package com.example.Ecommerce.dto.profile;

import com.example.Ecommerce.dto.extre.AttachDTO;
import com.example.Ecommerce.enums.Gender;
import com.example.Ecommerce.enums.ProfileRole;
import com.example.Ecommerce.enums.ProfileStatus;
import com.example.Ecommerce.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO  {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private ProfileStatus status;
    private String password;
    private ProfileRole role;
    private String jwt;
    private String imageId;
    private LocalDateTime createdDate;
    private AttachDTO attach;


}
