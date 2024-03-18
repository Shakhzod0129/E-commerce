package com.example.Ecommerce.dto.store;

import com.example.Ecommerce.dto.ProfileDTO;
import com.example.Ecommerce.dto.extre.AttachDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StoreInfoDTO {
    private Long id;
    private String name;
    private ProfileDTO profileId;
    private String description;
    private Integer quantityOfProduct;
    private Integer quantityOfOrder;
    private LocalDateTime createdDate;
    private Long countOfComment;
    private Double rateOfProducts;
    private AttachDTO attachId;
}
