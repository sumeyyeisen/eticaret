package com.proje.eticaret.service.impl;

import com.proje.eticaret.entity.Customer;
import com.proje.eticaret.repository.CustomerRepository;
import com.proje.eticaret.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
