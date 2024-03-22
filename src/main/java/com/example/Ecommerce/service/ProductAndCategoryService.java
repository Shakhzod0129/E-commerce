package com.example.Ecommerce.service;

import com.example.Ecommerce.entity.CategoryEntity;
import com.example.Ecommerce.entity.ProductAndCategoryEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.repository.ProductAndCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductAndCategoryService {

    @Autowired
    private ProductAndCategoryRepository productAndCategoryRepository;

    @Autowired
    private CategoryService categoryService;
    public void create(Long productId, List<Long> categoryIds, AppLanguage language){
        for (Long categoryId : categoryIds) {
            CategoryEntity categoryEntity = categoryService.get(categoryId, language);
            if(categoryEntity!=null){
                create(productId,categoryId);
            }
        }
    }

    public void create(Long productId, Long categoryId){
        ProductAndCategoryEntity entity=new ProductAndCategoryEntity();
        entity.setProductId(productId);
        entity.setCategoryId(categoryId);
        productAndCategoryRepository.save(entity);
    }

    //    @Transactional
    public void merge(Long productId, List<Long> newCategoryId) {
        // Eskilar ro'yhati
        List<ProductAndCategoryEntity> oldTypes = productAndCategoryRepository.findByProductId(productId);

//        Set<Integer> oldIds=new HashSet<>();
//        for (ArticleAndItsTypeEntity oldType : oldTypes) {
//            oldIds.add(oldType.getTypesId());
//        }
        Set<Long> oldTypeIds = oldTypes.stream()
                .map(ProductAndCategoryEntity::getCategoryId)
                .collect(Collectors.toSet());

        // Eskilar ro'yhatidan chiqarilgan yangi typelar

//        for (Integer categoryId : oldTypeIds) {
//            if (!newTypeIds.contains(typeId)) {
//                toDelete.add(typeId);
//            }

        List<Long> toDelete = oldTypeIds.stream()
                .filter(id -> !newCategoryId.contains(id))
                .collect(Collectors.toList());

        // Yangi typelarni qo'shish
        for (Long typeId : newCategoryId) {
            if (!oldTypeIds.contains(typeId)) {
                ProductAndCategoryEntity newTypeEntity = new ProductAndCategoryEntity();
                newTypeEntity.setProductId(productId);
                newTypeEntity.setCategoryId(typeId);
                productAndCategoryRepository.save(newTypeEntity);
            }
        }

        // Eskilar ro'yhatidan o'chirish
        for (Long categoryId : toDelete) {
            productAndCategoryRepository.deleteByProductIdAndCategoryId(productId, categoryId);
        }
    }
}
