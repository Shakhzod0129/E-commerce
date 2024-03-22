package com.example.Ecommerce.repository;

import com.example.Ecommerce.dto.product.ProductInfoMapper;
import com.example.Ecommerce.entity.ProductEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity,Long> {


    @Query(value = "select p.id from store s" +
            " inner join profile p on p.id = s.profile_id\n" +
            " where s.id=?1", nativeQuery = true)
    Long findProfile(Long storeId);

    @Modifying
    @Query("update ProductEntity set visible=false where id=?1")
    void deleteById(@NotNull Long productId);

    @Query(value = "select p.id                 as productId,\n" +
            "       p.description_uz     as productDescriptionUz,\n" +
            "       p.description_ru     as productDescriptionRu,\n" +
            "       p.description_en     as productDescriptionEn,\n" +
            "       p.name_uz            as productNameUz,\n" +
            "       p.name_ru            as productNameRu,\n" +
            "       p.name_en            as productNameEn,\n" +
            "       p.count_comments     as productCommentCount,\n" +
            "       p.count_orders       as productOrderCount,\n" +
            "       p.price              as ProductPrice,\n" +
            "       p.rate               as productRate,\n" +
            "       p.quantity           as productQuantity,\n" +
            "       p.store_id           as productStoreId,\n" +
            "       (select cast(json_agg(json_build_object('id', a.id, 'url', a.url, 'duration', a.duration)) as text)\n" +
            "        from attach a\n" +
            "                 inner join public.product_attach pa on a.id = pa.attach_id\n" +
            "                 inner join public.product pr on pr.id = pa.product_id\n" +
            "        where pr.id = p.id) as productAttachJson,\n" +
            "       (select cast(json_agg(json_build_object('id', a.id, a.name_uz, pa.value_uz, a.name_ru, pa.value_ru, a.name_en,\n" +
            "                                               pa.value_en)) as text)\n" +
            "        from attributes a\n" +
            "                 inner join public.product_attribute pa on a.id = pa.attribute_id\n" +
            "                 inner join public.product pr on pr.id = pa.product_id\n" +
            "        where pr.id = p.id) as productAttributeJson,\n" +
            "       (select cast(json_agg(json_build_object('id', c.id, 'name_uz', c.name_uz, 'name_ru', c.name_ru, 'name_en',\n" +
            "                                               c.name_en)) as text)\n" +
            "        from categories c\n" +
            "                 inner join public.product_category pc on c.id = pc.category_id\n" +
            "                 inner join public.product pr on pr.id = pc.product_id\n" +
            "        where pr.id = p.id) as productCategoryJson\n" +
            "from product p\n" +
            "         inner join public.store s on s.id = p.store_id",nativeQuery = true)
    Page<ProductInfoMapper> getProductInfo(Pageable pageable);

    @Query(value = "select p.id                 as productId,\n" +
            "       p.description_uz     as productDescriptionUz,\n" +
            "       p.description_ru     as productDescriptionRu,\n" +
            "       p.description_en     as productDescriptionEn,\n" +
            "       p.name_uz            as productNameUz,\n" +
            "       p.name_ru            as productNameRu,\n" +
            "       p.name_en            as productNameEn,\n" +
            "       p.count_comments     as productCommentCount,\n" +
            "       p.count_orders       as productOrderCount,\n" +
            "       p.price              as ProductPrice,\n" +
            "       p.rate               as productRate,\n" +
            "       p.quantity           as productQuantity,\n" +
            "       p.store_id           as productStoreId,\n" +
            "       pc.category_id       as productCategoryId,\n" +
            "    \n" +
            "       (select cast(json_agg(json_build_object('id', a.id, 'url', a.url, 'duration', a.duration)) as text)\n" +
            "        from attach a\n" +
            "                 inner join public.product_attach pa on a.id = pa.attach_id\n" +
            "                 inner join public.product pr on pr.id = pa.product_id\n" +
            "        where pr.id = p.id) as productAttachJson\n" +
            "from product p\n" +
            "         inner join public.store s on s.id = p.store_id\n" +
            "         inner join public.product_category pc on p.id = pc.product_id\n" +
            "where pc.category_id=?1",nativeQuery = true)
    Page<ProductInfoMapper> findByCategoryId(Long categoryId, Pageable pageable);
}
