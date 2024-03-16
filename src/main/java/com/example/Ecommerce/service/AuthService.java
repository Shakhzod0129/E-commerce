package com.example.Ecommerce.service;


import com.example.Ecommerce.dto.ProfileDTO;
import com.example.Ecommerce.dto.auth.AuthDTO;
import com.example.Ecommerce.dto.auth.RegistrationDTO;
import com.example.Ecommerce.dto.extre.JWTDTO;
import com.example.Ecommerce.entity.ProfileEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.enums.ProfileRole;
import com.example.Ecommerce.enums.ProfileStatus;
import com.example.Ecommerce.exp.AppBadException;
import com.example.Ecommerce.repository.EmailSentHistoryRepository;
import com.example.Ecommerce.repository.ProfileRepository;
import com.example.Ecommerce.utils.JWTUtil;
import com.example.Ecommerce.utils.MD5Util;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private MailSenderService mailSender;
    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    private EmailSentHistoryRepository emailSendHistoryRepository;

    /**
     * this method is used for profile login. In this, the necessary information is requested from the profile, and the profile database with this information is checked, and its status is checked.
     * If the checks are successful, the JWT is provided to the profile, otherwise an exception is thrown 👇🏻
     */

    public ProfileDTO login(AuthDTO auth, AppLanguage language) {
        Optional<ProfileEntity> optional =
                profileRepository.findByEmailAndPassword(auth.getEmail(), MD5Util.encode(auth.getPassword()));
        if (optional.isEmpty() || !optional.get().getVisible()) {
            log.warn("email.password.wrong {}", auth.getEmail());
            throw new AppBadException(resourceBundleService.getMessage("email.password.wrong", language));
        }
        if (!optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            log.warn("Profile not active {}", optional.get().getStatus());
            throw new AppBadException(resourceBundleService.getMessage("profile.not.active", language));
        }
        ProfileEntity entity = optional.get();
        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
//        dto.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));

        dto.setJwt(JWTUtil.encodeForSpringSecurity(entity.getEmail(), entity.getRole()));

        return dto;
    }

/**this method is used for user registration.
 If the information entered by the user meets the necessary conditions,
 a link will be sent to his email, otherwise an exception will be thrown 👇🏻*/
    public Boolean registration(RegistrationDTO dto, AppLanguage language) {
        if (!dto.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{5,}$")) {
            log.warn("Password required {}", dto.getPassword());
            throw new AppBadException(resourceBundleService.getMessage("password.required", language));
        }
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());
            } else {
                log.warn("Email exists {}", dto.getEmail());
                throw new AppBadException(resourceBundleService.getMessage("email.exists", language));
            }
        }
        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();
        if (emailSendHistoryRepository.countSendEmail(dto.getEmail(), from, to) >= 4) {
            log.warn("To many attempt. Please try after 1 minute. {}",dto.getEmail());
            throw new AppBadException(resourceBundleService.getMessage("after.1.minute",language));
        }
        registrationByEmail(dto);
        return true;

    }

    /**this method is used to unregister a user after using the link sent to register 👇🏻*/
    public ProfileDTO emailVerification(String jwt, AppLanguage language) {
            ProfileDTO profile = new ProfileDTO();
        try {
            JWTDTO jwtDTO = JWTUtil.decode(jwt);

            Optional<ProfileEntity> optional = profileRepository.findById(Long.valueOf(jwtDTO.getId()));
            if (optional.isEmpty()) {
                log.warn("Profile not found {}", jwtDTO.getId());
                throw new AppBadException(resourceBundleService.getMessage("profile.not.found", language));
            }
            ProfileEntity entity = optional.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                log.warn("Profile in wrong status {}", entity.getStatus());
                throw new AppBadException(resourceBundleService.getMessage("profile.in.wrong.status", language));
            }
            profileRepository.updateStatus(entity.getId(), ProfileStatus.ACTIVE);
            profile.setName(entity.getName());
            profile.setSurname(entity.getSurname());
            profile.setRole(entity.getRole());
            profile.setJwt(JWTUtil.encode(Math.toIntExact(entity.getId()), entity.getRole()));

        } catch (JwtException e) {
            log.warn("Please tyre again. {}", jwt);
            throw new AppBadException(resourceBundleService.getMessage("please.tyre.again", language));
        }
        return profile;
    }

    private void registrationByEmail(RegistrationDTO dto) {

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setPhoneNumber(dto.getPhone());
        entity.setGender(dto.getGender());
        entity.setImageId(dto.getImageId());
        profileRepository.save(entity);

        String jwt = JWTUtil.encodeForEmail(Math.toIntExact(entity.getId()));
        String text = "<h1 style=\"text-align: center\">Hello %s</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding: 30px\">To complete registration please link to the following link</p>\n" +
                "<a style=\" background-color: #f44336;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" href=\"http://localhost:8080/auth/verification/email/%s\n" +
                "\">Click</a>\n" +
                "<br>\n";
        text = String.format(text, entity.getName(), jwt);
        mailSender.sendEmail(dto.getEmail(), "Complete registration", text);

        emailHistoryService.create(dto, text);
    }



}
