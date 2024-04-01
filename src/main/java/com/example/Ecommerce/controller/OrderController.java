package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.order.OrderDTO;
import com.example.Ecommerce.dto.product.ProductDTO;
import com.example.Ecommerce.dto.store.CreateStoreDTO;
import com.example.Ecommerce.dto.store.StoreDTO;
import com.example.Ecommerce.dto.store.UpdateStoreDTO;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.service.OrderService;
import com.example.Ecommerce.utils.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(summary = "Api for Order", description = "this api used to create Order")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody OrderDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        Long id = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(orderService.create(id,dto,language));
    }

    @Operation(summary = "Cancel Order by ID", description = "Cancel an existing order by its ID")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("cancelByAdmin/{orderId}")
    public ResponseEntity<?> updateStoreByOwner(@PathVariable Long orderId,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        String message = orderService.cancelById(orderId, language);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Cancel Order by ID", description = "Cancel an existing order by its ID")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("cancelByOwner/{orderId}")
    public ResponseEntity<?> cancelOrderByIdForClient(@PathVariable Long orderId,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        Long id = SpringSecurityUtil.getCurrentUser().getId();
        String message = orderService.cancelOrderByIdForClient(orderId, id, language);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Change status 'DELIVERED'  by ID", description = "Change status 'DELIVERED' an existing order by its ID")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("deliveredByAdmin/{orderId}")
    public ResponseEntity<?> deliveredById(@PathVariable Long orderId,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        String message = orderService.deliveredById(orderId, language);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Change status 'ON_THE_WAY'  by ID", description = "Change status 'ON_THE_WAY' an existing order by its ID")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("onTheWayByAdmin/{orderId}")
    public ResponseEntity<?> onTheWayById(@PathVariable Long orderId,
                                           @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        String message = orderService.onTheWayById(orderId, language);
        return ResponseEntity.ok(message);
    }


    @Operation(summary = "Api for get", description = "this api used for get product by Pagination")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getByOrderId/{orderId}")
    public ResponseEntity<OrderDTO> getOrderByIdWithProducts(@PathVariable Long orderId,
                                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(orderService.getOrderByIdWithProducts2(orderId, language));
    }

    @Operation(summary = "Api for get", description = "this api used for get Oder list by Pagination")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getByPagination")
    public ResponseEntity<PageImpl<OrderDTO>> getByPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                              @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        Long id = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(orderService.getAllPagination(id,page, size, language));
    }



}
