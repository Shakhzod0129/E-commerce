package com.example.Ecommerce.repository;

import com.example.Ecommerce.entity.CartEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<CartEntity,Long> {

    @Query(value = "select p.id from cart c " +
            "inner join profile p on p.id=c.profile_id where c.id=?1",nativeQuery = true)
    Long profileId(Long cartId);
}
