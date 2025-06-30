package com.proje.eticaret.service.impl;

import com.proje.eticaret.dto.CartDTO;
import com.proje.eticaret.entity.Cart;
import com.proje.eticaret.entity.Item;
import com.proje.eticaret.entity.Product;
import com.proje.eticaret.exception.ResourceNotFoundException;
import com.proje.eticaret.mapper.CartMapper;
import com.proje.eticaret.repository.CartRepository;
import com.proje.eticaret.repository.ProductRepository;
import com.proje.eticaret.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @Override
    public CartDTO getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", id));
        return cartMapper.toDTO(cart);
    }

    @Override
    public List<CartDTO> getAllCarts() {
        return cartMapper.toDTOList(cartRepository.findAll());
    }

    @Override
    public CartDTO updateCart(Long id, CartDTO cartDTO) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", id));

        cart.setTotalPrice(cartDTO.getTotalPrice());
        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Override
    public void emptyCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", id));
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getCartByCustomerId(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "customerId", customerId));
        return cartMapper.toDTO(cart);
    }

    @Override
    public void addProductToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stok yetersiz.");
        }

        Optional<Item> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            Item item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            Item item = new Item();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setProductPrice(BigDecimal.valueOf(product.getPrice()));
            item.setCart(cart);
            cart.getItems().add(item);
        }

        product.setStock(product.getStock() - quantity);

        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getProductPrice().doubleValue() * i.getQuantity())
                .sum();

        cart.setTotalPrice(total);

        productRepository.save(product);
        cartRepository.save(cart);
    }
}
