package com.proje.eticaret.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductRequest {
    private Long productId;
    private Integer quantity;
}
