package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.product.ProductAndItsAttributeDTO;
import com.example.Ecommerce.entity.ProductAttributeEntity;
import com.example.Ecommerce.repository.ProductAndItsAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductAndItsAttributeService {

    @Autowired
    private ProductAndItsAttributeRepository productAndItsAttributeRepository;

    public String create(ProductAndItsAttributeDTO dto) {

        ProductAttributeEntity entity=new ProductAttributeEntity();

        entity.setProductId(dto.getProductId());
        entity.setAttributeId(dto.getAttributeId());
        entity.setValueUz(dto.getValueUz());
        entity.setValueRu(dto.getValueRu());
        entity.setValueEn(dto.getValueEn());
        productAndItsAttributeRepository.save(entity);

        return "Created";
    }
}
