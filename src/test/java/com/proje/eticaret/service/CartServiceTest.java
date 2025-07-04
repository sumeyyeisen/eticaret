package com.proje.eticaret.service;

import com.proje.eticaret.dto.CartDTO;
import com.proje.eticaret.entity.Cart;
import com.proje.eticaret.entity.Customer;
import com.proje.eticaret.mapper.CartMapper;
import com.proje.eticaret.repository.CartRepository;
import com.proje.eticaret.repository.CustomerRepository;
import com.proje.eticaret.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCartByCustomerId() {
        Customer customer = new Customer();
        customer.setId(1L);

        Cart cart = new Cart();
        cart.setCustomer(customer);

        CartDTO cartDTO = new CartDTO();
        cartDTO.setTotalPrice(100.0);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(cartRepository.findByCustomerId(1L)).thenReturn(Optional.of(cart));
        when(cartMapper.toDTO(cart)).thenReturn(cartDTO);

        CartDTO result = cartService.getCartByCustomerId(1L);
        assertEquals(cartDTO, result);
    }

}
