package com.example.Ecommerce.repository;

import com.example.Ecommerce.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity,Long> {

}
