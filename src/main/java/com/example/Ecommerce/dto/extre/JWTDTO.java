package com.example.Ecommerce.dto.extre;


import com.example.Ecommerce.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JWTDTO {
    private Integer id;

    public JWTDTO(String email, ProfileRole role) {
        this.email = email;
        this.role = role;
    }

    private String email;
    private ProfileRole role;

    public JWTDTO(Integer id) {
        this.id = id;
    }

    public JWTDTO(Integer id, ProfileRole role) {
        this.id = id;
        this.role = role;
    }

    public JWTDTO(Integer id, String email, ProfileRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
