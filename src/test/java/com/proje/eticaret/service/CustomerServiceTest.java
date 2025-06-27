package com.proje.eticaret.service;

import com.proje.eticaret.entity.Customer;
import com.proje.eticaret.repository.CustomerRepository;
import com.proje.eticaret.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Ali");
        customer.setEmail("ali@example.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer saved = customerService.addCustomer(customer);
        assertEquals("Ali", saved.getFirstName());
        assertEquals("ali@example.com", saved.getEmail());
    }
}
