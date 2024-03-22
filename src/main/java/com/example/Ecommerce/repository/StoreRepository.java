package com.example.Ecommerce.repository;

import com.example.Ecommerce.dto.store.StoreInfoMapper;
import com.example.Ecommerce.entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends CrudRepository<StoreEntity,Long> {


    Optional<StoreEntity> findByIdAndProfileId(Long storeId, Long ProfileId);


        @Query(value = "select s.id                  as storeId,\n" +
                "       s.name                as storeName,\n" +
                "       s.description_uz         as storeDescriptionUz,\n" +
                "       s.description_ru        as storeDescriptionRu,\n" +
                "       s.description_en         as storeDescriptionEn,\n" +
                "       s.status              as storetStatus,\n" +
                "       s.comment_count       as storeCommentCount,\n" +
                "       s.created_date        as storeCreatedDate,\n" +
                "       s.attach_id           as storeAttachId,\n" +
                "       s.quantity_of_order   as storeQuantityOfOrder,\n" +
                "       s.quantity_of_product as storeQuantityOfProduct,\n" +
                "\n" +
                "\n" +
                "       p.id                  as profileId,\n" +
                "       p.name as profileName,\n" +
                "       p.surname as profileSurname,\n" +
                "       p.gender as profileGender,\n" +
                "       p.phone_number as profilePhoneNumber,\n" +
                "       p.attach_id            as profileAttachId\n" +
                "\n" +
                "from store s\n" +
                "         inner join profile p\n" +
                "                    on p.id = s.profile_id\n" +
                "\n",nativeQuery = true)
        Page<StoreInfoMapper> getStoreInfo(Pageable pageable);

    List<StoreEntity> findByProfileId(Long profileId);

}
