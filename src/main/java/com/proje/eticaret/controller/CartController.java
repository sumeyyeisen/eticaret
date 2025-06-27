package com.proje.eticaret.controller;

import com.proje.eticaret.entity.Cart;
import com.proje.eticaret.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{customerId}")
    public Cart getCart(@PathVariable Long customerId) {
        return cartService.getCartByCustomerId(customerId);
    }

    @PostMapping("/{customerId}/add")
    public Cart addProductToCart(
            @PathVariable Long customerId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ) {
        return cartService.addProductToCart(customerId, productId, quantity);
    }

    @DeleteMapping("/{customerId}/remove")
    public Cart removeProductFromCart(
            @PathVariable Long customerId,
            @RequestParam Long productId
    ) {
        return cartService.removeProductFromCart(customerId, productId);
    }

    @PutMapping("/{customerId}/update")
    public Cart updateCartItemQuantity(
            @PathVariable Long customerId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ) {
        return cartService.updateCartItemQuantity(customerId, productId, quantity);
    }

    @PostMapping("/{customerId}/empty")
    public Cart emptyCart(@PathVariable Long customerId) {
        return cartService.emptyCart(customerId);
    }
}
