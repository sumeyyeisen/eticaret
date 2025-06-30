package com.proje.eticaret.service;

import com.proje.eticaret.dto.ProductDTO;
import com.proje.eticaret.entity.Product;
import com.proje.eticaret.repository.ProductRepository;
import com.proje.eticaret.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper; // <-- ModelMapper mocklandı

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(100.0);

        Product productEntity = new Product();
        productEntity.setName("Test Product");
        productEntity.setPrice(100.0);

        when(modelMapper.map(productDTO, Product.class)).thenReturn(productEntity);
        when(productRepository.save(any(Product.class))).thenReturn(productEntity);
        when(modelMapper.map(productEntity, ProductDTO.class)).thenReturn(productDTO);

        ProductDTO savedProductDTO = productService.createProduct(productDTO);

        assertEquals("Test Product", savedProductDTO.getName());
        assertEquals(100.0, savedProductDTO.getPrice());
    }
}
