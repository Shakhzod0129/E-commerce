package com.example.Ecommerce.dto.order;

import java.time.LocalDateTime;

public interface OrderInfoMapper {
    Long getOrderId();
    String getOrderStatus();
    String getOrderAddress();
    String getOrderContact();
    Long getOrderCartId();
    Long getOrderCustomerId();
    LocalDateTime getOrderCreatedDate();
}
