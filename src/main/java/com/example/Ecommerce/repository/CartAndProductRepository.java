package com.example.Ecommerce.repository;


import com.example.Ecommerce.entity.CartAndProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartAndProductRepository extends CrudRepository<CartAndProductEntity, Long> {
    List<CartAndProductEntity> findByCartId(Long cartId);

    @Transactional
    void deleteByCartIdAndProductId(Long cartId, Long productId);

    @Transactional
    @Query("delete from CartAndProductEntity where productId=?1")
    void deleteByProductId(Long id);
}
