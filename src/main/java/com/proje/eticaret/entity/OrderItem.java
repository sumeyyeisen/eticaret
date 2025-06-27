package com.proje.eticaret.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity{

    @ManyToOne
    private Order order;
    private BigDecimal productPrice;
    private Integer quantity;
    @ManyToOne
    private Product product;
}
