package com.proje.eticaret.service;

import com.proje.eticaret.entity.Customer;
import com.proje.eticaret.entity.Order;
import com.proje.eticaret.repository.CartRepository;
import com.proje.eticaret.repository.CustomerRepository;
import com.proje.eticaret.repository.OrderRepository;
import com.proje.eticaret.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrdersForCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        Order order = new Order();
        order.setCustomer(customer);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderRepository.findByCustomer(customer)).thenReturn(List.of(order));

        List<Order> orders = orderService.getOrdersForCustomer(1L);
        assertEquals(1, orders.size());
        assertEquals(customer, orders.get(0).getCustomer());
    }
}
