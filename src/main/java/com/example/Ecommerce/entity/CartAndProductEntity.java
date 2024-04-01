package com.example.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart_and_product")
public class CartAndProductEntity extends BaseEntity {

    @Column(name = "product_id")
    private Long productId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private ProductEntity product;

    @Column(name = "profile_id")
    private Long profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;


    @Column(name = "cart_id")
    private Long cartId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id",insertable = false,updatable = false)
    private CartEntity cart;

    @Column(name = "quantity")
    private Integer quantity=0;
}
