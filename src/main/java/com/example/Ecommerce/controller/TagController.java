package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.store.CreateStoreDTO;
import com.example.Ecommerce.dto.tag.TagDTO;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Operation(summary = "Api for Tag", description = "this api used to create tag")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody TagDTO dto) {
        return ResponseEntity.ok((tagService.create(dto)));
    }
}
