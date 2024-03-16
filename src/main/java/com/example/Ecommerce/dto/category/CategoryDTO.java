package com.example.Ecommerce.dto.category;

import com.example.Ecommerce.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Long id;
    private String name;
    private Long parentId;
    private Status status;
    private LocalDateTime createdDate;
}