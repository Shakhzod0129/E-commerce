package com.example.Ecommerce.dto.product;

import com.example.Ecommerce.dto.store.StoreDTO;
import com.example.Ecommerce.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Long id;
    private Long storeId;
    private List<Long> categoryId;
    private List<String> attachId;
    private Double price;
    private String name;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String description;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private ProductStatus status;
    private Double rate;
    private Long countComments;
    private Long countOrders;
    private Long quantity;
    private StoreDTO store;
    private String productAttachJson;
    private String productAttributeJson;
    private String productCategoryJson;
}
