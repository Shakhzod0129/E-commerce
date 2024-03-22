package com.example.Ecommerce.service;


import com.example.Ecommerce.entity.AttachEntity;
import com.example.Ecommerce.entity.ProductAndAttachEntity;
import com.example.Ecommerce.entity.ProductAndCategoryEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.repository.ProductAndAttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductAndAttachService {

    @Autowired
    private ProductAndAttachRepository productAndAttachRepository;

    @Autowired
    private AttachService attachService;
    public void create(Long productId, List<String> attachIds){
        for (String attachId : attachIds) {
            AttachEntity attachEntity = attachService.get(attachId);
            if(attachEntity!=null){
                create(productId,attachId);
            }
        }
    }

    public void create(Long productId, String attachId){
        ProductAndAttachEntity entity=new ProductAndAttachEntity();
        entity.setProductId(productId);
        entity.setAttachId(attachId);
        productAndAttachRepository.save(entity);
    }

    //    @Transactional
    public void merge(Long productId, List<String> newAttachId) {
        // Eskilar ro'yhati
        List<ProductAndAttachEntity> oldTypes = productAndAttachRepository.findByProductId(productId);

//        Set<Integer> oldIds=new HashSet<>();
//        for (ArticleAndItsTypeEntity oldType : oldTypes) {
//            oldIds.add(oldType.getTypesId());
//        }
        Set<String> oldTypeIds = oldTypes.stream()
                .map(ProductAndAttachEntity::getAttachId)
                .collect(Collectors.toSet());

        // Eskilar ro'yhatidan chiqarilgan yangi typelar

//        for (Integer categoryId : oldTypeIds) {
//            if (!newTypeIds.contains(typeId)) {
//                toDelete.add(typeId);
//            }

        List<String> toDelete = oldTypeIds.stream()
                .filter(id -> !newAttachId.contains(id))
                .collect(Collectors.toList());

        // Yangi typelarni qo'shish
        for (String typeId : newAttachId) {
            if (!oldTypeIds.contains(typeId)) {
                ProductAndAttachEntity newTypeEntity = new ProductAndAttachEntity();
                newTypeEntity.setProductId(productId);
                newTypeEntity.setAttachId(typeId);
                productAndAttachRepository.save(newTypeEntity);
            }
        }

        // Eskilar ro'yhatidan o'chirish
        for (String attachId : toDelete) {
            productAndAttachRepository.deleteByProductIdAndAttachId(productId, attachId);
        }
    }

}

