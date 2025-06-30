package com.proje.eticaret.service;

import com.proje.eticaret.dto.CustomerDTO;
import com.proje.eticaret.entity.Customer;
import com.proje.eticaret.mapper.CustomerMapper;
import com.proje.eticaret.repository.CartRepository;
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

    @Mock
    private CartRepository cartRepository;  // CartRepository mock eklendi

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Ali");
        customerDTO.setEmail("ali@example.com");

        Customer customerEntity = new Customer();
        customerEntity.setFirstName("Ali");
        customerEntity.setEmail("ali@example.com");

        when(customerMapper.toEntity(any(CustomerDTO.class))).thenReturn(customerEntity);

        when(customerRepository.save(any(Customer.class))).thenReturn(customerEntity);

        when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(customerMapper.toDTO(any(Customer.class))).thenReturn(customerDTO);

        CustomerDTO savedCustomerDTO = customerService.addCustomer(customerDTO);

        assertEquals("Ali", savedCustomerDTO.getFirstName());
        assertEquals("ali@example.com", savedCustomerDTO.getEmail());
    }
}
