package com.example.Ecommerce.entity;

import com.example.Ecommerce.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity {

    @Column(name = "store_id")
    private Long storeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private StoreEntity store;

    @Column(name = "category_id")
    private Long categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "price",nullable = false)
    private Double price;

    @Column(name = "name_uz")
    private String nameUz;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "description_uz")
    private String descriptionUz;
    @Column(name = "description_ru")
    private String descriptionRu;
    @Column(name = "description_en")
    private String descriptionEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status = ProductStatus.ACTIVE;

    @Column(name = "rate")
    private Double rate=0d;

    @Column(name = "count_comments")
    private Long countComments=0L;

    @Column(name = "count_orders")
    private Long countOrders=0L;

    @Column(name = "quantity")
    private Long quantity=0L;

}
