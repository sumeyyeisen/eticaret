package com.proje.eticaret.service;

import com.proje.eticaret.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product product);
    Optional<Product>getProductById(Long id);
    List<Product>getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);

}
