package com.example.Ecommerce.repository;

import com.example.Ecommerce.dto.order.OrderInfoMapper;
import com.example.Ecommerce.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    @Query(value = "select p.id from orders o \n" +
            "inner join public.cart c on c.id = o.cart_id\n" +
            "inner join public.profile p on p.id = c.profile_id", nativeQuery = true)
    Long findProfileIdByCartId(Long id);


    @Query(value = "select o.id                as orderId,\n" +
            "       o.status            as orderStatus,\n" +
            "       o.delivered_address as orderAddress,\n" +
            "       o.delivered_contact as orderContact,\n" +
            "       o.cart_id           as orderCartId,\n" +
            "       o.customer_id       as orderCustomerId,\n" +
            "       o.created_date      as orderCreatedDate\n" +
            "from orders o\n" +
            "         inner join public.cart c on c.id = o.cart_id\n" +
            "where o.id = ?1", nativeQuery = true)
    Optional<OrderInfoMapper> getInfoOrder(Long orderId);

    Page<OrderEntity> findAll(Pageable pageable);
}
