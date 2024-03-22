package com.example.Ecommerce.repository;

import com.example.Ecommerce.entity.ProductAndCategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductAndCategoryRepository extends CrudRepository<ProductAndCategoryEntity, Long> {
    List<ProductAndCategoryEntity> findByProductId(Long productId);

    @Transactional
    void deleteByProductIdAndCategoryId(Long productId, Long categoryId);

}
