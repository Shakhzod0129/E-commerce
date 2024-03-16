package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.ProductDTO;
import com.example.Ecommerce.entity.ProductEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.enums.ProductStatus;
import com.example.Ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;


    public String create(ProductDTO productDTO, AppLanguage language) {

        ProductEntity entity =new ProductEntity();

        entity.setName(productDTO.getName());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPrice(productDTO.getPrice());
        entity.setStatus(ProductStatus.ACTIVE);

        productRepository.save(entity);
        return "Product has created";
    }
}
