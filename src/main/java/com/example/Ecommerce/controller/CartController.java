package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.cart.CartDTO;
import com.example.Ecommerce.dto.store.CreateStoreDTO;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.service.CartService;
import com.example.Ecommerce.utils.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Operation(summary = "Api for cart", description = "this api used to create cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(cartService.create(profileId,language));
    }
    @Operation(summary = "Api for cart", description = "this api used to add product to cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CartDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(cartService.addProducts(profileId,dto,language));
    }

    @Operation(summary = "Delete product from cart by ID")
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProducts(@RequestBody CartDTO dto,
                                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        String message = cartService.deleteByProductId(profileId,dto, language);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Api for cart", description = "this api used to update product to cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CartDTO dto,
                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(cartService.updateProducts(profileId,dto,language));
    }






}
