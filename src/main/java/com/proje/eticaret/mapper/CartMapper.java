package com.proje.eticaret.mapper;

import com.proje.eticaret.dto.CartDTO;
import com.proje.eticaret.entity.Cart;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    private final ModelMapper modelMapper;

    public CartMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartDTO toDTO(Cart cart) {
        if(cart == null) return null;
        CartDTO dto = modelMapper.map(cart, CartDTO.class);
        return dto;
    }

    public Cart toEntity(CartDTO dto) {
        if(dto == null) return null;
        Cart cart = modelMapper.map(dto, Cart.class);
        return cart;
    }

    public List<CartDTO> toDTOList(List<Cart> carts) {
        return carts.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
