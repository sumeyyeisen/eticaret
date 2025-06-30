package com.proje.eticaret.service;

import com.proje.eticaret.dto.OrderDTO;
import com.proje.eticaret.entity.Cart;
import com.proje.eticaret.entity.Customer;
import com.proje.eticaret.entity.Item;
import com.proje.eticaret.entity.Order;
import com.proje.eticaret.mapper.OrderMapper;
import com.proje.eticaret.repository.CartRepository;
import com.proje.eticaret.repository.CustomerRepository;
import com.proje.eticaret.repository.ItemRepository;
import com.proje.eticaret.repository.OrderRepository;
import com.proje.eticaret.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder() {
        Customer customer = new Customer();
        customer.setId(1L);

        Item item = new Item();
        item.setId(10L);
        item.setQuantity(2);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setTotalPrice(200.0);
        cart.setItems(new ArrayList<>(List.of(item)));

        customer.setCart(cart);

        Order order = new Order();
        order.setId(1L);
        order.setOrderDate(LocalDateTime.now());
        order.setCustomer(customer);
        order.setItems(List.of(item));
        order.setTotalPrice(BigDecimal.valueOf(200.0));

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setTotalPrice(order.getTotalPrice());

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.placeOrder(1L);

        assertNotNull(result);
        assertEquals(new BigDecimal("200.0"), result.getTotalPrice());

        verify(orderRepository).save(any(Order.class));
        verify(cartRepository).save(any(Cart.class));
    }
}
