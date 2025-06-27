package com.proje.eticaret.service;

import com.proje.eticaret.entity.Product;
import com.proje.eticaret.repository.ProductRepository;
import com.proje.eticaret.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {

        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(5000.0);
        product.setStock(10);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product saved = productService.createProduct(product);

        assertNotNull(saved);
        assertEquals("Laptop", saved.getName());
        assertEquals(5000.0, saved.getPrice());
        assertEquals(10, saved.getStock());
        verify(productRepository, times(1)).save(product);
    }
}
