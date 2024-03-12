package com.example.Ecommerce.repository;

import com.example.Ecommerce.entity.ProductAttributeEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductAndItsAttributeRepository extends CrudRepository<ProductAttributeEntity,Long> {

}
