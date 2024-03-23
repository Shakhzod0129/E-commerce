package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.cart.CartDTO;
import com.example.Ecommerce.entity.CartEntity;
import com.example.Ecommerce.entity.ProductEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.exp.AppBadException;
import com.example.Ecommerce.repository.CartAndProductRepository;
import com.example.Ecommerce.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ResourceBundleService resourceBundleService;

    @Autowired
    private CartAndProductService cartAndProductService;
    @Autowired
    private CartAndProductRepository cartAndProductRepository;
    @Autowired
    private ProductService productService;

    public String create(Long profileId, AppLanguage language) {
        CartEntity entity = new CartEntity();

        entity.setProfileId(profileId);
        cartRepository.save(entity);

        return resourceBundleService.getMessage("cart.created", language);
    }


    public String addProduct(Long profileId, CartDTO dto, AppLanguage language){

        CartEntity entity = get(dto.getId(), language);
        Long id = cartRepository.profileId(dto.getId());
        if(!Objects.equals(profileId,id)){
            log.warn("cart not allowed");
            throw new AppBadException(resourceBundleService.getMessage("cart.not.allowed",language));
        }

        int countProduct = dto.getProductId().size();

        entity.setQuantityOfProducts(entity.getQuantityOfProducts()+countProduct);
        List<Long> productIds = dto.getProductId();

        Double totalPrice=0d;
        for (Long productId : productIds) {
            ProductEntity productEntity = productService.get(productId, language);
            totalPrice+=productEntity.getPrice();
        }
        entity.setTotalPrice(entity.getTotalPrice()+totalPrice);
        cartRepository.save(entity);

        cartAndProductService.create(entity.getId(),dto.getProductId(),language);

        return resourceBundleService.getMessage("add.product",language);
    }

    public String deleteProduct(Long profileId,CartDTO dto,AppLanguage language){

        CartEntity entity = get(dto.getId(), language);
        Long id = cartRepository.profileId(entity.getId());
        if(!Objects.equals(profileId,id)){
            log.warn("cart not allowed");
            throw new AppBadException(resourceBundleService.getMessage("cart.not.allowed",language));
        }

        List<Long> productIds = dto.getProductId();


        for (Long productId : productIds) {
            ProductEntity productEntity = productService.get(productId, language);
            entity.setTotalPrice(entity.getTotalPrice()-productEntity.getPrice());
            entity.setQuantityOfProducts(entity.getQuantityOfProducts()-1);
        }
        cartRepository.save(entity);
        cartAndProductService.delete(entity.getId(),dto.getProductId(),language);

        return "Product has deleted";
    }


    public CartEntity get(Long cartId,AppLanguage language){
        return cartRepository.findById(cartId).orElseThrow(() -> {
            log.warn("Store not found{}", cartId);
            return new AppBadException(resourceBundleService.getMessage("cart.not.found", language));
        });
    }


}

