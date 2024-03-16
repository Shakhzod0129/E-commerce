package com.example.Ecommerce.controller;


import com.example.Ecommerce.dto.CreatedProfileDTO;
import com.example.Ecommerce.dto.ProfileDTO;
import com.example.Ecommerce.dto.UpdateProfileDTO;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.service.ProfileService;
import com.example.Ecommerce.utils.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class is created for the methods of the profile
 */
@Slf4j
@Tag(name = "Profile API list", description = "API list for Profile")
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    /**
     * this method is used to create profiles on the admin side
     */
    @PostMapping("/adm")
    @Operation(summary = "Api for create", description = "this api is used to create profile ")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CreatedProfileDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("Create profile {}", dto.getEmail());
        return ResponseEntity.ok(profileService.create(dto, language));
    }

    /**
     * this method is used to change each profile's own password
     */
    @PostMapping("/any/{password}")
    @Operation(summary = "Api for changePassword", description = "this api is used to change password ")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR')")
    public ResponseEntity<?> changePassword(@PathVariable String password,
                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("change password profile ");
        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(profileService.changePassword(profileId, password, language));
    }

    @PostMapping("/any")
    @Operation(summary = "Api for updateEmail", description = "this api is used to updated email ")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR')")
    public ResponseEntity<?> updateEmail(@RequestBody(required = false) UpdateProfileDTO dto,
                                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("update  email ");
        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        String name = SpringSecurityUtil.getCurrentUser().getName();
        return ResponseEntity.ok(profileService.updateEmail(profileId,name,dto,language));
    }
    @GetMapping("/verification/email/{jwt}")
    @Operation( summary = "Api for verification by email", description = "this api used for authorization")
    public ResponseEntity<ProfileDTO> emailVerification(@PathVariable("jwt") String jwt,
                                                        @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        log.info("verification {}", jwt);
        return ResponseEntity.ok(profileService.emailVerification(jwt,language));
    }

    @PostMapping("/any/detail")
    @Operation(summary = "Api for updateProfileDetail", description = "this api is used to updated detail ")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR')")
    public ResponseEntity<?> updateProfileDetail(@RequestBody(required = false) UpdateProfileDTO dto,
                                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("update detail profile ");
        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(profileService.updateProfileDetail(profileId, dto, language));
    }
    @PostMapping("/any/photo")
    @Operation(summary = "Api for updateProfileAttach", description = "this api is used to updated photo ")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR')")
    public ResponseEntity<?> updateProfileAttach(@RequestParam("file") MultipartFile file,
                                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("change photo profile ");
        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(profileService.updateProfileAttach(profileId, file, language));
    }

    @GetMapping("/any/getDetails")
    @Operation(summary = "Api for getDetails", description = "this method works to return the information of the user who made the request ")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR')")
    public ResponseEntity<?> getDetails(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("change photo profile ");
        Long profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(profileService.getDetails(profileId, language));
    }





}
