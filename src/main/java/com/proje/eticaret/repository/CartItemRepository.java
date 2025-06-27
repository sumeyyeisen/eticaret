package com.proje.eticaret.repository;

import com.proje.eticaret.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
