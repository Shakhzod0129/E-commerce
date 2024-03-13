package com.example.Ecommerce.service;


import com.example.Ecommerce.dto.auth.EmailHistoryDTO;
import com.example.Ecommerce.dto.auth.RegistrationDTO;
import com.example.Ecommerce.entity.EmailSentHistoryEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.repository.EmailSentHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class EmailHistoryService {
    @Autowired
    private EmailSentHistoryRepository emailSentHistoryRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    /**
     * this method is needed to write a link to the history when an sms is sent to an email 👇🏻
     */

    public void create(RegistrationDTO dto, String text) {
        EmailSentHistoryEntity entity = new EmailSentHistoryEntity();
        entity.setEmail(dto.getEmail());
        entity.setMessage(text);
        emailSentHistoryRepository.save(entity);
    }

    /**
     * this method is needed to write to the history when SMS is
     * sent to the newly entered email when the user changes the email address. 👇🏻
     */
    public void create(String email, String text) {
        EmailSentHistoryEntity entity = new EmailSentHistoryEntity();
        entity.setEmail(email);
        entity.setMessage(text);
        emailSentHistoryRepository.save(entity);
    }

    /**
     * this method is needed to get all sms history. 👇🏻
     */
    public PageImpl<EmailHistoryDTO> getAllEmailHistory(Pageable pageable, AppLanguage language) {
        Page<EmailSentHistoryEntity> all = emailSentHistoryRepository.findAll(pageable);
        if (all.isEmpty()) {
            throw new ArithmeticException(resourceBundleService.getMessage("history.empty", language));
        }
        List<EmailSentHistoryEntity> content = all.getContent();
        List<EmailHistoryDTO> dtoList = new LinkedList<>();
        for (EmailSentHistoryEntity entity : content) {
            dtoList.add(toDo(entity));
        }
        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }

    private EmailHistoryDTO toDo(EmailSentHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    /**
     * this method is needed to get all sms history by entered email. 👇🏻
     */
    public PageImpl<EmailHistoryDTO> getAllEmailHistoryByEmail(String email, Pageable pageable, AppLanguage language) {
        Page<EmailSentHistoryEntity> byEmail = emailSentHistoryRepository.findByEmail(email, pageable);
        if (byEmail.isEmpty()) {
            throw new ArithmeticException(resourceBundleService.getMessage("no.history.found.for.this.email", language));
        }
        List<EmailSentHistoryEntity> content = byEmail.getContent();
        List<EmailHistoryDTO> dtoList = new LinkedList<>();
        for (EmailSentHistoryEntity entity : content) {
            dtoList.add(toDo(entity));
        }
        return new PageImpl<>(dtoList, pageable, byEmail.getTotalElements());
    }


}
