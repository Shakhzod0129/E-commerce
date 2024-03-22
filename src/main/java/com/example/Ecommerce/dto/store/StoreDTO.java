package com.example.Ecommerce.dto.store;

import com.example.Ecommerce.dto.profile.ProfileDTO;
import com.example.Ecommerce.dto.extre.AttachDTO;
import com.example.Ecommerce.enums.Status;
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
    private String description;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private Integer quantityOfProduct;
    private Integer quantityOfOrder;
    private LocalDateTime createdDate;
    private Long countOfComment;
    private Double rateOfProducts;
    private String attachId;
    private Status status;
    private AttachDTO attach;
    private ProfileDTO profile;
}
