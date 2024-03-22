package com.example.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_tags")
public class ProductAndTagNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private ProductEntity product;

    @Column(name = "tags_id")
    private Long tagsId;
    @ManyToOne
    @JoinColumn(name = "tags_id",insertable = false,updatable = false)
    private TagEntity types;
}
