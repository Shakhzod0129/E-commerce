package com.example.Ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreDTO {
    private Long id;
    private String name;
    private Long profileId;
    private Integer quantityOfProduct;
    private Integer quantityOfOrder;
    private LocalDateTime createdDate;
    private Long countOfComment;
    private Double rateOfProducts;
}
