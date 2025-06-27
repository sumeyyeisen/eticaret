package com.proje.eticaret.repository;

import com.proje.eticaret.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
