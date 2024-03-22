package com.example.Ecommerce.dto.store;

import com.example.Ecommerce.enums.Gender;
import com.example.Ecommerce.enums.Status;

import java.time.LocalDateTime;

public interface StoreInfoMapper {
    Long getStoreId();
    String getStoreName();
//    String getStoreDescription();
    String getStoreDescriptionUz();
    String getStoreDescriptionRu();
    String getStoreDescriptionEn();
    Status getStoreStatus();
    Long getStoreCommentCount();
    LocalDateTime getStoreCreatedDate();
    String getStoreAttachId();
    Integer getStoreQuantityOfOrder();
    Integer getStoreQuantityOfProduct();

    Long getProfileId();
    String getProfileName();
    String getProfileSurname();
    Gender getProfileGender();
    String getProfilePhoneNumber();
    String getProfileAttachId();
}
