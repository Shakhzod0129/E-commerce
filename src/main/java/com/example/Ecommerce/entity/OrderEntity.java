package com.example.Ecommerce.entity;

import com.example.Ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customer_id")
    private Long customerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",insertable = false,updatable = false)
    private ProfileEntity customer;
    
    @Column(name = "cart_id")
    private Long cartId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id",insertable = false,updatable = false)
    private CartEntity cart;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    
    @Column(name = "delivered_address")
    private String deliveredAddress;
    
    @Column(name = "delivered_contact")
    private String deliveredContact;


    // Getter va setterlar
}
