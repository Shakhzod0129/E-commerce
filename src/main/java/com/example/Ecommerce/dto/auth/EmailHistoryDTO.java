package com.example.Ecommerce.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryDTO {
    private Integer id;
    private LocalDateTime createdDate;
    private String sentSms;
    private String email;
}
