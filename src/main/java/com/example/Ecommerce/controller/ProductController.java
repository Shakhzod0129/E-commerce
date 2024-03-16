package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.ProductDTO;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation( summary = "Api for create product", description = "this api used for product")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductDTO productDTO,
                                    @RequestParam(required = false, defaultValue = "UZ") AppLanguage language) {

        return ResponseEntity.ok(productService.create(productDTO,language));
    }
}
