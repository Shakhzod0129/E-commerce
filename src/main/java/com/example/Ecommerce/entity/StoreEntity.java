package com.example.Ecommerce.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "store")
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_id")
    private Long profileId;
    @OneToOne
    @JoinColumn(name = "profile_Id",insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(name = "quantity_of_product")
    private Integer quantityOfProduct;

    @Column(name = "quantity_of_order")
    private Integer quantityOfOrder;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}