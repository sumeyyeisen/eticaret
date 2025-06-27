package com.proje.eticaret.service;

import com.proje.eticaret.entity.Cart;

public interface CartService {

    Cart getCartByCustomerId(Long customerId);

    Cart addProductToCart(Long customerId, Long productId, Integer quantity);

    Cart removeProductFromCart(Long customerId, Long productId);

    Cart updateCartItemQuantity(Long customerId, Long productId, Integer quantity);

    Cart emptyCart(Long customerId);
}
