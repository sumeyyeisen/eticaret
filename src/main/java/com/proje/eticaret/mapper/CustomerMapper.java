package com.proje.eticaret.mapper;

import com.proje.eticaret.dto.CustomerDTO;
import com.proje.eticaret.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private final ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerDTO toDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public Customer toEntity(CustomerDTO dto) {
        return modelMapper.map(dto, Customer.class);
    }
}
