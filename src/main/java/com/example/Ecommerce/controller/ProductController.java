package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.ProductDTO;
import com.example.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.create(productDTO));
    }
}
