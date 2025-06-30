package com.proje.eticaret.service;

import com.proje.eticaret.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(Long customerId);
    OrderDTO getOrderByCode(String code);
    List<OrderDTO> getAllOrdersForCustomer(Long customerId);
}
