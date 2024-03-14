package com.example.Ecommerce.controller;

import com.example.Ecommerce.configuration.CustomUserDetails;
import com.example.Ecommerce.dto.extre.AttachDTO;
import com.example.Ecommerce.service.AttachService;
import com.example.Ecommerce.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/attach")
public class AttachController {

    @Autowired
    private AttachService attachService;

//    @PostMapping("/upload")
//    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
//        String fileName = attachService.saveToSystem(file);
//        return ResponseEntity.ok().body(fileName);
//    }

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO dto = attachService.save(file);
        return ResponseEntity.ok().body(dto);
    }


    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                return this.attachService.loadImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE  )
    public byte[] open_general(@PathVariable("fileName") String fileName) {

        if (fileName != null && fileName.length() > 0) {
            try {
                return attachService.open_general(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

//    @GetMapping("/download/{attachId}")
//    public ResponseEntity<byte[]> download(@PathVariable("attachId") String attachId) {
//        byte[] fileData = attachService.loadImage2(attachId);
//
//
//        if (fileData != null) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", attachId);
//
//            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        return attachService.download(fileName);
    }


    @DeleteMapping("/adm/delete/{attachId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable("attachId") String attachId){
//        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(attachService.delete(attachId));
    }






}
