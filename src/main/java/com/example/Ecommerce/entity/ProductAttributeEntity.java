package com.example.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_attribute")
public class ProductAttributeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private ProductEntity product;


    @Column(name = "attribute_id")
    private Long attributeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id",insertable = false,updatable = false)
    private AttributeEntity attribute;

    @Column(name = "value")
    private String value;

    // Getters va setters
}
