package com.proje.eticaret.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDTO {
    private Long id;
    private Double totalPrice;
    private CustomerDTO customer;
    private List<ItemDTO> items;
}
