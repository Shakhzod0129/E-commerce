package com.example.Ecommerce.controller;


import com.example.Ecommerce.dto.profile.ProfileDTO;
import com.example.Ecommerce.dto.auth.AuthDTO;
import com.example.Ecommerce.dto.auth.RegistrationDTO;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Authorization Api list", description = "Api list for Authorization")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    @Operation( summary = "Api for login", description = "this api used for authorization")
    public ResponseEntity<?> login(@RequestBody AuthDTO auth,
                                   @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        log.info("Login {} ", auth.getEmail());
        return ResponseEntity.ok(authService.login(auth,language));
    }
    @PostMapping("/registration")
    @Operation( summary = "Api for registration", description = "this api used for authorization")
    public ResponseEntity<?> registartion( @Valid @RequestBody RegistrationDTO auth,
                                           @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        log.info("registration {}", auth.getEmail());
        return ResponseEntity.ok(authService.registration(auth,language));
    }
    @GetMapping("/verification/email/{jwt}")
    @Operation( summary = "Api for verification by email", description = "this api used for authorization")
    public ResponseEntity<ProfileDTO> emailVerification(@PathVariable("jwt") String jwt,
                                                        @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        log.info("verification {}", jwt);
        return ResponseEntity.ok(authService.emailVerification(jwt,language));
    }


}
