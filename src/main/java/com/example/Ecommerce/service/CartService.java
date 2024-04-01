package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.cart.CartDTO;
import com.example.Ecommerce.entity.CartAndProductEntity;
import com.example.Ecommerce.entity.CartEntity;
import com.example.Ecommerce.entity.ProductEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.exp.AppBadException;
import com.example.Ecommerce.repository.CartAndProductRepository;
import com.example.Ecommerce.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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


//
    public String addProducts(Long profileId,CartDTO dto, AppLanguage language) {
        CartEntity entity = get(dto.getId(), language);
        Long id = cartRepository.profileId(dto.getId());
        if(!Objects.equals(profileId,id)){
            log.warn("Cart not allowed");
            throw new AppBadException(resourceBundleService.getMessage("cart.not.allowed", language));
        }

        double totalPrice = 0d;
        for (Map.Entry<Long, Integer> entry : dto.getProductIdAndItsQuantity().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            ProductEntity productEntity = productService.get(productId, language);
            totalPrice += productEntity.getPrice() * quantity;
        }

        int totalQuantity = dto.getProductIdAndItsQuantity().values().stream().mapToInt(Integer::intValue).sum();
        entity.setQuantityOfProducts(entity.getQuantityOfProducts() + totalQuantity);
        entity.setTotalPrice(entity.getTotalPrice() + totalPrice);
        cartRepository.save(entity);

//        List<Long> productIds = new ArrayList<>(dto.getProductIdAndItsQuantity().keySet());
        cartAndProductService.create(entity.getId(),dto.getProductIdAndItsQuantity(), language);


        return resourceBundleService.getMessage("add.product", language);
    }

    public String  deleteByProductId(Long profileId,CartDTO dto,AppLanguage language) {
        CartEntity entity = get(dto.getId(), language);
        Long id = cartRepository.profileId(dto.getId());
        if (!Objects.equals(profileId, id)) {
            log.warn("Cart not allowed");
            throw new AppBadException(resourceBundleService.getMessage("cart.not.allowed", language));
        }
        List<Long> productIds = dto.getProductIds();
        if (productIds != null) {
            for (Long productId : productIds) {
                CartAndProductEntity cartAndProductEntity = cartAndProductRepository.findByCartIdAndProductId(entity.getId(), productId);
                if (cartAndProductEntity != null) {
                    cartAndProductRepository.deleteByCartIdAndProductId(entity.getId(), productId);
                    CartEntity cartEntity = get(entity.getId(), language);
                    ProductEntity productEntity = productService.get(productId, language);
                    Double price = productEntity.getPrice();
                    Integer quantity = cartAndProductEntity.getQuantity();
                    cartEntity.setTotalPrice(cartEntity.getTotalPrice() - (price * quantity));
                    cartEntity.setQuantityOfProducts(cartEntity.getQuantityOfProducts() - quantity);
                    cartRepository.save(cartEntity);
                }
            }
        } else {
            log.warn("ProductIds is null");
            throw new AppBadException("Product ids are null");
        }
        return "Products have been deleted";
    }


//    public String updateProducts(Long profileId, CartDTO dto, AppLanguage language) {
//        // Savdo kartasini bazadan tanlash
//        CartEntity entity = get(dto.getId(), language);
//        // Savdo kartasi profil identifikatorini olish
//        Long cartProfileId = cartRepository.profileId(dto.getId());
//        // Agar berilgan profil identifikatori va savdo kartasi profili mos emas bo'lsa, xato qaytariladi
//        if (!Objects.equals(profileId, cartProfileId)) {
//            log.warn("Cart not allowed");
//            throw new AppBadException(resourceBundleService.getMessage("cart.not.allowed", language));
//        }
//
//        // Berilgan mahsulotlar ro'yxati va ularning soni bo'yicha umumiy narx va mahsulotlar sonini hisoblash
//        double totalPrice = 0d;
//        int totalQuantity = 0;
//        for (Map.Entry<Long, Integer> entry : dto.getProductIdAndItsQuantity().entrySet()) {
//            Long productId = entry.getKey();
//            Integer quantity = entry.getValue();
//            // Mahsulot ma'lumotlar bazasidan olinadi
//            ProductEntity productEntity = productService.get(productId, language);
//            // Mahsulotning umumiy narxi va sonini hisoblash
//            double productPrice = productEntity.getPrice() * quantity;
//            totalPrice += productPrice;
//            totalQuantity += quantity;
//            // Mahsulotni savdo kartasiga qo'shish yoki yangilash
//            cartAndProductService.addOrUpdateProductToCart(entity.getId(), productId, quantity, language);
//        }
//
//        // Savdo kartasining umumiy narx va mahsulotlar sonini yangilash
//        entity.setQuantityOfProducts(totalQuantity);
//        entity.setTotalPrice(totalPrice);
//        // Yangilangan savdo kartasini saqlash
//        cartRepository.save(entity);
//
//        // Mahsulotlar muvaffaqiyatli yangilandi
//        return resourceBundleService.getMessage("update.products.success", language);
//    }

    // Savdo kartasiga mahsulot qo'shish yoki yangilash metodini yaratish

    public String updateProducts(Long profileId, CartDTO dto, AppLanguage language) {
        // Savdo kartasini bazadan tanlash
        CartEntity entity = get(dto.getId(), language);
        // Savdo kartasi profil identifikatorini olish
        Long cartProfileId = cartRepository.profileId(dto.getId());
        // Agar berilgan profil identifikatori va savdo kartasi profili mos bo'lmasa, xato qaytariladi
        if (!Objects.equals(profileId, cartProfileId)) {
            log.warn("Cart not allowed");
            throw new AppBadException(resourceBundleService.getMessage("cart.not.allowed", language));
        }

        // Berilgan mahsulotlar ro'yxati va ularning soni bo'yicha umumiy narx va mahsulotlar sonini hisoblash
        double totalPrice = 0d;
        int totalQuantity = 0;
        for (Map.Entry<Long, Integer> entry : dto.getProductIdAndItsQuantity().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            // Mahsulot ma'lumotlar bazasidan olinadi
            ProductEntity productEntity = productService.get(productId, language);
            // Mahsulotning umumiy narxi va sonini hisoblash
            double productPrice = productEntity.getPrice() * quantity;
            totalPrice += productPrice;
            totalQuantity += quantity;
            // Tekshirish
            if (quantity <= productEntity.getQuantity()&&quantity>0) { // Check if requested quantity is less than or equal to available quantity
                // Mahsulotni savdo kartasiga qo'shish yoki yangilash
                cartAndProductService.addOrUpdateProductToCart(entity.getId(), productId, quantity, language);
            } else {
                // If the requested quantity is more than available quantity, throw an exception
                throw new AppBadException(resourceBundleService.getMessage("cart.quantity.exceeded", language)+"id : "+productEntity.getId()+" - "+productEntity.getQuantity());
            }
        }

        // Savdo kartasining umumiy narx va mahsulotlar sonini yangilash
        entity.setQuantityOfProducts(totalQuantity);
        entity.setTotalPrice(totalPrice);
        // Yangilangan savdo kartasini saqlash
        cartRepository.save(entity);

        // Mahsulotlar muvaffaqiyatli yangilandi
        return resourceBundleService.getMessage("update.products.success", language);
    }





    public CartEntity get(Long cartId,AppLanguage language){
        return cartRepository.findById(cartId).orElseThrow(() -> {
            log.warn("Store not found{}", cartId);
            return new AppBadException(resourceBundleService.getMessage("cart.not.found", language));
        });
    }


}

