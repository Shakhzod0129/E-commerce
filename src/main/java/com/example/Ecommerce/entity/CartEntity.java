package com.example.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class CartEntity extends BaseEntity{
    @Column(name = "profile_id")
    private Long profileId;

    @OneToOne
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(name = "total_price")
    private Double totalPrice=0d;
    @Column(name = "quantity_product")
    private Integer quantityOfProducts=0;
}
