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

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",insertable = false,updatable = false)
    private CategoryEntity category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;


}