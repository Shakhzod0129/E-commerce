package com.example.Ecommerce.entity;

import com.example.Ecommerce.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_uz",unique = true,nullable = false)
    private String nameUz;
    @Column(name = "name_ru",unique = true,nullable = false)
    private String nameRu;
    @Column(name = "name_en",unique = true,nullable = false)
    private String nameEn;


    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",insertable = false,updatable = false)
    private CategoryEntity category;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;


}