package com.example.Ecommerce.service;

import com.example.Ecommerce.entity.ProductAndTagNameEntity;
import com.example.Ecommerce.repository.ProductAndTagNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAndTagNameService {

    @Autowired
    private ProductAndTagNameRepository productAndTagNameRepository;

    public void create(Long productId, List<Long> tagIdList) {
        for (Long typeId : tagIdList) {
            create(productId, typeId);
        }
    }

    public void create(Long productId, Long tagId) {
        ProductAndTagNameEntity entity = new ProductAndTagNameEntity();
        entity.setProductId(productId);
        entity.setTagsId(tagId);
        productAndTagNameRepository.save(entity);
    }
}
