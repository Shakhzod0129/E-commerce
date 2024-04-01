package com.example.Ecommerce.dto.order;

import com.example.Ecommerce.dto.product.ProductDTO;
import com.example.Ecommerce.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private Long id;
    private Long customerId;
    private Long cartId;
    private LocalDateTime createdDate;
    private OrderStatus status;
    private String deliveredAddress;
    private String deliveredContact;
    private List<ProductDTO> product;
    private Map<Long,Integer> products;
}
