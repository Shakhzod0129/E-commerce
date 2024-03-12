package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.ProductAndItsAttributeDTO;
import com.example.Ecommerce.entity.ProductAttributeEntity;
import com.example.Ecommerce.repository.ProductAndItsAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductAndItsAttributeService {

    @Autowired
    private ProductAndItsAttributeRepository productAndItsAttributeRepository;

    public String create(ProductAndItsAttributeDTO productDTO) {

        ProductAttributeEntity entity=new ProductAttributeEntity();

        entity.setProductId(productDTO.getProductId());
        entity.setAttributeId(productDTO.getAttributeId());
        entity.setValue(productDTO.getValue());
        productAndItsAttributeRepository.save(entity);

        return "Created";
    }
}
