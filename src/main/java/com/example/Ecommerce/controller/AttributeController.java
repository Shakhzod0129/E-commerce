package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.attribute.AttributeDTO;
import com.example.Ecommerce.dto.attribute.CreateAttributeDTO;
import com.example.Ecommerce.service.AttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "attributes API list", description = "API list for attributes")
@RestController
@RequestMapping("/attribute")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @Operation( summary = "Api for create attribute", description = "this api used for attribute")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateAttributeDTO attributeDTO) {
        return ResponseEntity.ok(attributeService.create(attributeDTO));
    }

}
