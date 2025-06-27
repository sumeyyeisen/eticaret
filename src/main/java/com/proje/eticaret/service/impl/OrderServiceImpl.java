package com.proje.eticaret.service.impl;

import com.proje.eticaret.entity.*;
import com.proje.eticaret.exception.CustomerNotFoundException;
import com.proje.eticaret.exception.EmptyCartException;
import com.proje.eticaret.exception.InsufficientStockException;
import com.proje.eticaret.repository.*;
import com.proje.eticaret.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public Order placeOrder(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("ID'si " + customerId + " olan müşteri bulunamadı."));

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new EmptyCartException("Sepet bulunamadı. Sipariş verilemez."));

        if (cart.getCartItems().isEmpty()) {
            throw new EmptyCartException("Sepet boş. Sipariş verilemez.");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setCode(UUID.randomUUID().toString());

        BigDecimal toplamFiyat = BigDecimal.ZERO;

        for (CartItem sepetUrunu : cart.getCartItems()) {
            Product urun = sepetUrunu.getProduct();

            if (urun.getStock() < sepetUrunu.getQuantity()) {
                throw new InsufficientStockException("Ürün '" + urun.getName() + "' için yeterli stok yok.");
            }

            urun.setStock(urun.getStock() - sepetUrunu.getQuantity());
            productRepository.save(urun);

            OrderItem siparisUrunu = new OrderItem();
            siparisUrunu.setOrder(order);
            siparisUrunu.setProduct(urun);
            siparisUrunu.setQuantity(sepetUrunu.getQuantity());
            siparisUrunu.setProductPrice(BigDecimal.valueOf(urun.getPrice()));

            order.getOrderItems().add(siparisUrunu);

            BigDecimal itemFiyat = BigDecimal.valueOf(urun.getPrice())
                    .multiply(BigDecimal.valueOf(sepetUrunu.getQuantity()));
            toplamFiyat = toplamFiyat.add(itemFiyat);
        }

        order.setTotalPrice(toplamFiyat);

        orderRepository.save(order);

        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return order;
    }

    @Override
    public Order getOrderByCode(String code) {
        return orderRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Kod ile eşleşen sipariş bulunamadı: " + code));
    }

    @Override
    public List<Order> getOrdersForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("ID'si " + customerId + " olan müşteri bulunamadı."));
        return orderRepository.findByCustomer(customer);
    }
}
