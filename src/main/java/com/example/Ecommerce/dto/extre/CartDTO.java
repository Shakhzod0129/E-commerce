package com.example.Ecommerce.dto.extre;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDTO {
    private Long id;
    private Long productId;
    private Long profileId;
    private LocalDateTime createdDate;
}
