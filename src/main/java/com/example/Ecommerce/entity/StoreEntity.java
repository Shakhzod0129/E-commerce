package com.example.Ecommerce.entity;

import com.example.Ecommerce.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "store")
public class StoreEntity extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "profile_id")
    private Long profileId;
    @OneToOne
    @JoinColumn(name = "profile_Id",insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity_of_product")
    private Integer quantityOfProduct=0;

    @Column(name = "quantity_of_order")
    private Integer quantityOfOrder=0;

    @Column(name = "comment_count")
    private Long commentCount=0L;

    @Column(name = "reate_products")
    private Double rete;

    @Column(name = "attach_id")
    private String attachId;
    @OneToOne
    @JoinColumn (name = "attach_id",insertable = false,updatable = false)
    private AttachEntity attach;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;


}