package com.proje.eticaret.service.impl;

import com.proje.eticaret.entity.*;
import com.proje.eticaret.exception.*;
import com.proje.eticaret.repository.*;
import com.proje.eticaret.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Cart getCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("ID'si " + customerId + " olan müşterinin sepeti bulunamadı"));
    }

    @Override
    public Cart addProductToCart(Long customerId, Long productId, Integer quantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("ID'si " + customerId + " olan müşteri yok"));

        Cart cart = cartRepository.findByCustomerId(customerId).orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            cart.setTotalPrice(0.0);
            cart = cartRepository.save(cart);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("ID'si " + productId + " olan ürün yok"));

        if (product.getStock() < quantity) {
            throw new InsufficientStockException("ID'si " + productId + " olan ürün stokta yetersiz");
        }

        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst();

        CartItem cartItem;
        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;

            if (product.getStock() < newQuantity) {
                throw new InsufficientStockException("ID'si " + productId + " olan ürün istenilen miktarda stokta yok");
            }

            cartItem.setQuantity(newQuantity);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getCartItems().add(cartItem);
        }

        updateCartTotal(cart);
        cartRepository.save(cart);
        cartItemRepository.save(cartItem);

        return cart;
    }

    @Override
    public Cart removeProductFromCart(Long customerId, Long productId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("ID'si " + customerId + " olan müşteriye ait sepet yok"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Sepette ID'si " + productId + " olan ürün yok"));

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        updateCartTotal(cart);
        cartRepository.save(cart);

        return cart;
    }

    @Override
    public Cart updateCartItemQuantity(Long customerId, Long productId, Integer quantity) {
        if (quantity <= 0) {
            return removeProductFromCart(customerId, productId);
        }

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("ID'si " + customerId + " olan müşteriye ait sepet yok"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Sepette ID'si " + productId + " olan ürün yok"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("ID'si " + productId + " olan ürün yok"));

        if (product.getStock() < quantity) {
            throw new InsufficientStockException("ID'si " + productId + " olan üründen yeterli stok yok");
        }

        cartItem.setQuantity(quantity);
        updateCartTotal(cart);

        cartRepository.save(cart);
        cartItemRepository.save(cartItem);

        return cart;
    }

    @Override
    public Cart emptyCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found for customer ID: " + customerId));

        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return cart;
    }

    private void updateCartTotal(Cart cart) {
        double total = 0.0;
        for (CartItem ci : cart.getCartItems()) {
            total += ci.getProduct().getPrice() * ci.getQuantity();
        }
        cart.setTotalPrice(total);
    }
}
