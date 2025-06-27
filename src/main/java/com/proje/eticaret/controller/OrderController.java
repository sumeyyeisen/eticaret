package com.proje.eticaret.controller;

import com.proje.eticaret.entity.Order;
import com.proje.eticaret.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Sipariş oluştur (sepeti siparişe çevir)
    @PostMapping("/place/{customerId}")
    public Order placeOrder(@PathVariable Long customerId) {
        return orderService.placeOrder(customerId);
    }

    // Siparişi kod ile getir
    @GetMapping("/{code}")
    public Order getOrderByCode(@PathVariable String code) {
        return orderService.getOrderByCode(code);
    }

    // Müşterinin tüm siparişlerini getir
    @GetMapping("/customer/{customerId}")
    public List<Order> getOrdersForCustomer(@PathVariable Long customerId) {
        return orderService.getOrdersForCustomer(customerId);
    }
}
