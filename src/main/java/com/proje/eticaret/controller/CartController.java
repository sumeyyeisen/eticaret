package com.proje.eticaret.controller;

import com.proje.eticaret.dto.AddProductRequest;
import com.proje.eticaret.dto.CartDTO;
import com.proje.eticaret.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @GetMapping
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable Long id, @RequestBody CartDTO cartDTO) {
        return ResponseEntity.ok(cartService.updateCart(id, cartDTO));
    }

    @PutMapping("/{id}/empty")
    public ResponseEntity<Void> emptyCart(@PathVariable Long id) {
        cartService.emptyCart(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/add-product")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long id, @RequestBody AddProductRequest request) {
        cartService.addProductToCart(id, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }
}
