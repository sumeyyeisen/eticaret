package com.proje.eticaret.service;

import com.proje.eticaret.dto.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO getCartById(Long id);
    List<CartDTO> getAllCarts();
    CartDTO updateCart(Long id, CartDTO cartDTO);
    void emptyCart(Long id);
    void addProductToCart(Long cartId, Long productId, Integer quantity);
    CartDTO getCartByCustomerId(Long customerId);
}
