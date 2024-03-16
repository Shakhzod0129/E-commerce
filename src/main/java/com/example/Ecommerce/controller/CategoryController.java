package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.category.CategoryDTO;
import com.example.Ecommerce.dto.category.CreateCategoryDTO;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Api for registration", description = "this api used for authorization")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateCategoryDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        return ResponseEntity.ok(categoryService.create(dto, language));
    }

    @Operation(summary = "Update category by ID", description = "Update an existing category by its ID")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId,
                                            @RequestBody CreateCategoryDTO dto,
                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        String message = categoryService.updateByCategoryId(categoryId, dto, language);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Delete category by ID")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId,
                                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        String message = categoryService.deleteByCategoryId(categoryId, language);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDTO>> getCategoryList() {
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        return ResponseEntity.ok(categoryDTOList);
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByParentId(@PathVariable Long parentId) {
        List<CategoryDTO> categories = categoryService.listByParentId(parentId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/categories/search")
    public ResponseEntity<List<CategoryDTO>> searchCategories(@RequestParam String query,
                                                              @RequestParam(required = false, defaultValue = "UZ") AppLanguage language) {
        List<CategoryDTO> categories = categoryService.searchCategories(query, language);
        return ResponseEntity.ok(categories);
    }
}
