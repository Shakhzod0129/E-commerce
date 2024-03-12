package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.AttributeDTO;
import com.example.Ecommerce.dto.ProductDTO;
import com.example.Ecommerce.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attributes")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AttributeDTO attributeDTO) {
        return ResponseEntity.ok(attributeService.create(attributeDTO));
    }
}
