package com.proje.eticaret.service;

import com.proje.eticaret.entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long customerId);
    Order getOrderByCode(String code);
    List<Order> getOrdersForCustomer(Long customerId);
}
