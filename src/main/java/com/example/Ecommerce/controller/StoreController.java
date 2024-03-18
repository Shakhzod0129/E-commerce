package com.example.Ecommerce.controller;

import com.example.Ecommerce.configuration.CustomUserDetails;
import com.example.Ecommerce.dto.category.CreateCategoryDTO;
import com.example.Ecommerce.dto.store.CreateStoreDTO;
import com.example.Ecommerce.dto.store.StoreDTO;
import com.example.Ecommerce.dto.store.UpdateStoreDTO;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.service.StoreService;
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
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Operation(summary = "Api for Store", description = "this api used to create store")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid  @RequestBody CreateStoreDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        return ResponseEntity.ok(storeService.create(dto, language));
    }


    @Operation(summary = "Update store by ID", description = "Update an existing store by its ID")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PutMapping("/{storeId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long storeId,
                                            @Valid @RequestBody UpdateStoreDTO dto,
                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        String message = storeService.updateStoreByOwner(storeId,profileId, dto, language);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Api for get", description = "this api used for get store by Pagination")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getByPagination")
    public ResponseEntity<PageImpl<StoreDTO>> getByPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                              @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(storeService.pagination(page, size, language));
    }

    @Operation(summary = "Api for get", description = "this api used for get list of store")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getList")
    public ResponseEntity<?> getList(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language){
            return ResponseEntity.ok(storeService.getList(language));
    }

    @Operation(summary = "Api to get stores by profile ID", description = "This API is used to get stores by profile ID")
    @GetMapping("/get-by-profile")
    public ResponseEntity<List<StoreDTO>> getStoresByProfileId(@RequestParam("profileId") Long profileId,
                                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        List<StoreDTO> stores = storeService.getStoresByProfileId(profileId, language);
        return ResponseEntity.ok(stores);
    }


}
