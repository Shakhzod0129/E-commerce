package com.example.Ecommerce.entity;

import com.example.Ecommerce.enums.Gender;
import com.example.Ecommerce.enums.ProfileRole;
import com.example.Ecommerce.enums.ProfileStatus;
import com.example.Ecommerce.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Profile")
public class ProfileEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status=ProfileStatus.ACTIVE;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;

    @Column(name = "attcha_Id")
    private String imageId;

    @ManyToOne
    @JoinColumn(name = "attach_Id",insertable = false,updatable = false)
    private AttachEntity attach;

}
