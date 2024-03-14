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


    @Column(name = "attribute_name")
    private String attributeName;

    // Boshqa xususiyatlarga oid ma'lumotlar

    // Getters va setters
}