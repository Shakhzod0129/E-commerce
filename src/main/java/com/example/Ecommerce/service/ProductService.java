package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.ProductDTO;
import com.example.Ecommerce.entity.ProductEntity;
import com.example.Ecommerce.enums.ProductStatus;
import com.example.Ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String create(ProductDTO productDTO) {

        ProductEntity entity =new ProductEntity();

        entity.setName(productDTO.getName());
//        entity.setStoreId(12L);
//        entity.setCategoryId(34L);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPrice(productDTO.getPrice());
//        entity.setRate(0D);
//        entity.setDescription("New");
        entity.setStatus(ProductStatus.ACTIVE);

        productRepository.save(entity);
        return "Product has created";
    }
}
