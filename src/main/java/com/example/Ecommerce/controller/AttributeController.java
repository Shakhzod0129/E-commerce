package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.AttributeDTO;
import com.example.Ecommerce.service.AttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "attributes API list", description = "API list for attributes")
@RestController
@RequestMapping("/attribute")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @Operation( summary = "Api for create attribute", description = "this api used for attribute")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody AttributeDTO attributeDTO) {
        return ResponseEntity.ok(attributeService.create(attributeDTO));
    }

}
