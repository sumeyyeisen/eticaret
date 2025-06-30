package com.proje.eticaret.service.impl;

import com.proje.eticaret.dto.CustomerDTO;
import com.proje.eticaret.entity.Cart;
import com.proje.eticaret.entity.Customer;
import com.proje.eticaret.mapper.CustomerMapper;
import com.proje.eticaret.repository.CartRepository;
import com.proje.eticaret.repository.CustomerRepository;
import com.proje.eticaret.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);

        Customer savedCustomer = customerRepository.save(customer);
        cartRepository.save(cart);

        return customerMapper.toDTO(savedCustomer);
    }
}
