package com.example.Ecommerce.service;

import com.example.Ecommerce.entity.CartAndProductEntity;
import com.example.Ecommerce.entity.CartEntity;
import com.example.Ecommerce.entity.ProductEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.repository.CartAndProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartAndProductService {

    @Autowired
    private CartAndProductRepository cartAndProductRepository;

    @Autowired
    private ProductService productService;

    @Lazy
    @Autowired
    private CartService cartService;


    public void create(Long cartId, List<Long> productIds, AppLanguage language){
        for (Long productId : productIds) {
            ProductEntity productEntity = productService.get(productId, language);
            if(productEntity!=null){
                create(cartId,productId,language);
            }
        }
    }

    public void create(Long cartId, Long productId,AppLanguage language){
        CartEntity entity1 = cartService.get(cartId, language);
        CartAndProductEntity entity=new CartAndProductEntity();
        entity.setCartId(cartId);
        entity.setProductId(productId);
        entity.setProfileId(entity1.getProfileId());
        cartAndProductRepository.save(entity);
    }

    public void delete(Long cartId, List<Long> productIds, AppLanguage language){
        for (Long productId : productIds) {
            ProductEntity productEntity = productService.get(productId, language);
            cartAndProductRepository.deleteByCartIdAndProductId(cartId,productEntity.getId());
        }
    }
    public void merge(Long cartId, List<Long> newProductIds) {
        // Eskilar ro'yhati
        List<CartAndProductEntity> oldTypes = cartAndProductRepository.findByCartId(cartId);

//        Set<Integer> oldIds=new HashSet<>();
//        for (ArticleAndItsTypeEntity oldType : oldTypes) {
//            oldIds.add(oldType.getTypesId());
//        }
        Set<Long> oldTypeIds = oldTypes.stream()
                .map(CartAndProductEntity::getProductId)
                .collect(Collectors.toSet());

        // Eskilar ro'yhatidan chiqarilgan yangi typelar

//        for (Integer categoryId : oldTypeIds) {
//            if (!newTypeIds.contains(typeId)) {
//                toDelete.add(typeId);
//            }

        List<Long> toDelete = oldTypeIds.stream()
                .filter(id -> !newProductIds.contains(id))
                .collect(Collectors.toList());

        // Yangi typelarni qo'shish
        for (Long productId : newProductIds) {
            if (!oldTypeIds.contains(productId)) {
                CartAndProductEntity newTypeEntity = new CartAndProductEntity();
                newTypeEntity.setProductId(cartId);
                newTypeEntity.setProductId(productId);
                cartAndProductRepository.save(newTypeEntity);
            }
        }

        // Eskilar ro'yhatidan o'chirish
        for (Long productId : toDelete) {
            cartAndProductRepository.deleteByCartIdAndProductId(cartId, productId);
        }
    }
}
