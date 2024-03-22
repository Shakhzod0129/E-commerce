package com.example.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "attributes")
public class AttributeEntity extends BaseEntity {

    @Column(name = "name_uz",nullable = false,unique = true)
    private String nameUz;
    @Column(name = "name_ru",nullable = false,unique = true)
    private String nameRu;
    @Column(name = "name_en",nullable = false,unique = true)
    private String nameEn;

}