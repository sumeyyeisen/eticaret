package com.proje.eticaret.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Item extends BaseEntity {

    @ManyToOne
    private Product product;
    private Integer quantity;
    private BigDecimal productPrice;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Order order;
}
