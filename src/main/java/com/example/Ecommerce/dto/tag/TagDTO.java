package com.example.Ecommerce.dto.tag;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
    private Long id;
    @NotBlank(message = "Tag name is required")
    @Size(min = 3, max = 100, message = "Tag name must be between 3 and 100 characters long")
    private String tagName;
    private LocalDateTime createdDate;
}
