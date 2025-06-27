package com.proje.eticaret.repository;

import com.proje.eticaret.entity.Customer;
import com.proje.eticaret.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByCode(String code);
    List<Order> findByCustomer(Customer customer);
}
