package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.product.CreateProductDTO;
import com.example.Ecommerce.dto.product.ProductDTO;
import com.example.Ecommerce.dto.product.UpdateProductDTO;
import com.example.Ecommerce.dto.store.StoreDTO;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.service.ProductService;
import com.example.Ecommerce.utils.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Api for create product", description = "this api used for product")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateProductDTO productDTO,
                                    @RequestParam(required = false, defaultValue = "UZ") AppLanguage language) {

        Long id = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(productService.create(id, productDTO, language));
    }

    @Operation(summary = "Update product by ID", description = "Update an existing product by its ID")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @Valid @RequestBody UpdateProductDTO dto,
                                           @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        String message = productService.update(productId, profileId, dto, language);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Delete category by ID")
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long productId,
                                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        String message = productService.delete(productId, profileId, language);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Api for get", description = "this api used for get product by Pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getByPagination")
    public ResponseEntity<PageImpl<ProductDTO>> getByPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(productService.pagination(page, size, language));
    }

    @Operation(summary = "Api for get", description = "this api used for get product by Pagination")
    @GetMapping("/getByPaginationByCategoryId")
    public ResponseEntity<PageImpl<ProductDTO>> getByPaginationByCategoryId(@RequestParam Long categoryId,
                                                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(productService.paginationByCategoryId(page, size,categoryId, language));
    }

    @Operation(summary = "Api for get", description = "this api used for get product by Pagination")
    @GetMapping("/getLast10Product")
    public ResponseEntity<List<ProductDTO>> getLast10Product(@RequestParam Long categoryId,
                                                                        @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(productService.getLast10Product(categoryId, language));
    }
}
