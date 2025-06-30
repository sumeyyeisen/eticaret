package com.proje.eticaret.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String code;
    private BigDecimal totalPrice;
    private LocalDateTime createdDate;
    private Long customerId;
    private List<Long> itemIds;
}
