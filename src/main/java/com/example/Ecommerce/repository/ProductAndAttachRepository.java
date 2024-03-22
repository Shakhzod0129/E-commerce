package com.example.Ecommerce.repository;

import com.example.Ecommerce.entity.ProductAndAttachEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductAndAttachRepository extends CrudRepository<ProductAndAttachEntity,Long> {
    List<ProductAndAttachEntity> findByProductId(Long productId);

    @Transactional
    void deleteByProductIdAndAttachId(Long productId, String attachId);
}
