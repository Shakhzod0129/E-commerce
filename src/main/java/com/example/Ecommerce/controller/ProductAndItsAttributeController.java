package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.ProductAndItsAttributeDTO;
import com.example.Ecommerce.dto.ProductDTO;
import com.example.Ecommerce.service.ProductAndItsAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product_attribute")
public class ProductAndItsAttributeController {

    @Autowired
    private ProductAndItsAttributeService productAndItsAttributeService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductAndItsAttributeDTO productDTO) {
        return ResponseEntity.ok(productAndItsAttributeService.create(productDTO));
    }
}
