package com.proje.eticaret.repository;

import com.proje.eticaret.entity.Cart;
import com.proje.eticaret.entity.Item;
import com.proje.eticaret.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCart(Cart cart);
    List<Item> findByOrder(Order order);
}

