package com.proje.eticaret.mapper;

import com.proje.eticaret.dto.OrderDTO;
import com.proje.eticaret.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = modelMapper.map(order, OrderDTO.class);
        dto.setCustomerId(order.getCustomer().getId());
        dto.setItemIds(order.getItems()
                .stream()
                .map(item -> item.getId())
                .collect(Collectors.toList()));
        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        Order order = modelMapper.map(dto, Order.class);
        return order;
    }
}
