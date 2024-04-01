package com.example.Ecommerce.service;

import com.example.Ecommerce.entity.CartAndProductEntity;
import com.example.Ecommerce.entity.CartEntity;
import com.example.Ecommerce.entity.ProductEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.exp.AppBadException;
import com.example.Ecommerce.repository.CartAndProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    @Autowired
    private ResourceBundleService resourceBundleService;

//    public void create(Long cartId, List<Long> productIds, AppLanguage language){
//        for (Long productId : productIds) {
//            ProductEntity productEntity = productService.get(productId, language);
//            if(productEntity!=null){
//                create(cartId,productId,language);
//            }
//        }
//    }
//
//    private void create(Long cartId, Long productId, AppLanguage language) {
//        CartEntity cartEntity = cartService.get(cartId, language);
//        CartAndProductEntity cartAndProductEntity = cartAndProductRepository.findByCartIdAndProductId(cartEntity.getId(), productId);
//
//        if (cartAndProductEntity == null) {
//            cartAndProductEntity = new CartAndProductEntity();
//            cartAndProductEntity.setCartId(cartId);
//            cartAndProductEntity.setProductId(productId);
//            cartAndProductEntity.setProfileId(cartEntity.getProfileId());
//            cartAndProductEntity.setQuantity(1); // Boshlang'ich miqdor
//        } else {
//            cartAndProductEntity.setQuantity(cartAndProductEntity.getQuantity() + 1); // Quantity ni oshirish
//        }
//
//        cartAndProductRepository.save(cartAndProductEntity);
//    }

//    public void delete(Long cartId, List<Long> productIds, AppLanguage language){
//        for (Long productId : productIds) {
//            ProductEntity productEntity = productService.get(productId, language);
//            cartAndProductRepository.deleteByCartIdAndProductId(cartId,productEntity.getId());
//        }
//    }


//    public void update(Long cartId, Long productId, Integer quantity) {
//        CartAndProductEntity cartProduct = cartAndProductRepository.findByCartIdAndProductId(cartId, productId);
//        if (cartProduct != null) {
//            cartProduct.setQuantity(quantity);
//            cartAndProductRepository.save(cartProduct);
//        } else {
//            throw new AppBadException("Cart product not found");
//        }
//    }

    public void create(Long cartId, Map<Long, Integer> productIdAndItsQuantity, AppLanguage language) {
        for (Map.Entry<Long, Integer> entry : productIdAndItsQuantity.entrySet()) {
            Long productId = entry.getKey();
            ProductEntity productEntity = productService.get(productId, language);
            Integer quantity = entry.getValue();
//            create(cartId, productId, quantity, language);
            if (quantity <= productEntity.getQuantity() && quantity > 0) { // Check if requested quantity is less than or equal to available quantity
                // Mahsulotni savdo kartasiga qo'shish yoki yangilash
                create(cartId, productId, quantity, language);
            } else {
                // If the requested quantity is more than available quantity, throw an exception
                throw new AppBadException(resourceBundleService.getMessage("cart.quantity.exceeded", language) + "id : " + productEntity.getId() + " - " + productEntity.getQuantity());
            }
        }
    }

    private void create(Long cartId, Long productId, Integer quantity, AppLanguage language) {
        CartEntity cartEntity = cartService.get(cartId, language);
        CartAndProductEntity cartAndProductEntity = cartAndProductRepository.findByCartIdAndProductId(cartId, productId);

        if (cartAndProductEntity == null) {
            cartAndProductEntity = new CartAndProductEntity();
            cartAndProductEntity.setCartId(cartId);
            cartAndProductEntity.setProductId(productId);
            cartAndProductEntity.setProfileId(cartEntity.getProfileId());
            cartAndProductEntity.setQuantity(quantity);
        } else {
            cartAndProductEntity.setQuantity(cartAndProductEntity.getQuantity() + quantity);
        }

        cartAndProductRepository.save(cartAndProductEntity);
    }

    public void addOrUpdateProductToCart(Long cartId, Long productId, Integer quantity, AppLanguage language) {
        // Savdo kartasida mavjud mahsulotni qidirish
        CartAndProductEntity cartAndProductEntity = cartAndProductRepository.findByCartIdAndProductId(cartId, productId);
        if (cartAndProductEntity == null) {
            // Agar mahsulot savdo kartasida mavjud bo'lmasa, yangi qo'shish
            cartAndProductEntity = new CartAndProductEntity();
            cartAndProductEntity.setCartId(cartId);
            cartAndProductEntity.setProductId(productId);
            cartAndProductEntity.setProfileId(cartId); // Profile ID ni saqlash uchun savdo kartasining ID sini o'rniga qo'yamiz
            cartAndProductEntity.setQuantity(quantity);
        } else {
            // Agar mahsulot savdo kartasida mavjud bo'lsa, uning sonini yangilash
            cartAndProductEntity.setQuantity(quantity);
        }
        // Mahsulotni saqlash yoki yangilash
        cartAndProductRepository.save(cartAndProductEntity);
    }

}
