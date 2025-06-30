package com.proje.eticaret.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal productPrice;
}
