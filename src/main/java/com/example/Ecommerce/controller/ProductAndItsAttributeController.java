package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.ProductAndItsAttributeDTO;
import com.example.Ecommerce.dto.ProductDTO;
import com.example.Ecommerce.service.ProductAndItsAttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "product_attribute API list", description = "API list for product_attribute")
@RestController
@RequestMapping("/product_attribute")
public class ProductAndItsAttributeController {

    @Autowired
    private ProductAndItsAttributeService productAndItsAttributeService;

    @Operation( summary = "Api for create product_attribute", description = "this api used for product_attribute")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductAndItsAttributeDTO productDTO) {
        return ResponseEntity.ok(productAndItsAttributeService.create(productDTO));
    }
}
