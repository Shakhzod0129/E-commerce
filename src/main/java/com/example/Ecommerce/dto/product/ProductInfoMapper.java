package com.example.Ecommerce.dto.product;

import com.fasterxml.jackson.databind.JsonNode;

public interface ProductInfoMapper {
    Long getProductId();
    String getProductDescriptionUz();
    String getProductDescriptionRu();
    String getProductDescriptionEn();
    String getProductNameUz();
    String getProductNameRu();
    String getProductNameEn();
    Long getProductCommentCount();
    Long getProductOrderCount();
    Double getProductPrice();
    Double getProductRate();
    Long getProductQuantity();
    Long getProductStoreId();
    Long getProductCategoryId();
    String getProductAttachJson();
    String getProductAttributeJson();
    String getProductCategoryJson();
}
