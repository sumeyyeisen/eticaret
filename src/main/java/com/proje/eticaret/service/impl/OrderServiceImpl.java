package com.proje.eticaret.service.impl;

import com.proje.eticaret.dto.OrderDTO;
import com.proje.eticaret.entity.Cart;
import com.proje.eticaret.entity.Customer;
import com.proje.eticaret.entity.Item;
import com.proje.eticaret.entity.Order;
import com.proje.eticaret.exception.ResourceNotFoundException;
import com.proje.eticaret.mapper.OrderMapper;
import com.proje.eticaret.repository.CartRepository;
import com.proje.eticaret.repository.CustomerRepository;
import com.proje.eticaret.repository.ItemRepository;
import com.proje.eticaret.repository.OrderRepository;
import com.proje.eticaret.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO placeOrder(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        Cart cart = customer.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty.");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(BigDecimal.valueOf(cart.getTotalPrice()));
        order.setCode(UUID.randomUUID().toString());

        for (Item item : cart.getItems()) {
            item.setOrder(order);
            item.setCart(null);
        }

        order.setItems(cart.getItems());

        cart.getItems().clear();
        cart.setTotalPrice(0.0);

        cartRepository.save(cart);
        orderRepository.save(order);

        return orderMapper.toDTO(order);
    }

    @Override
    public OrderDTO getOrderByCode(String code) {
        Order order = orderRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "code", code));
        return orderMapper.toDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrdersForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        return orderRepository.findByCustomer(customer)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }
}
