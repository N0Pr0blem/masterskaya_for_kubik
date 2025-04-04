package com.kubik.masterskaya.service.impl;

import com.kubik.masterskaya.dto.product.ProductRequestDto;
import com.kubik.masterskaya.entity.Product;
import com.kubik.masterskaya.exception.ApiException;
import com.kubik.masterskaya.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findAll_ShouldReturnAllProducts() {
        // Arrange
        List<Product> expectedProducts = List.of(
                new Product(1L, "Product1", 100, "Desc1", "url1", "Brand1", "CarBrand1"),
                new Product(2L, "Product2", 200, "Desc2", "url2", "Brand2", "CarBrand2")
        );
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productService.findAll();

        // Assert
        assertEquals(expectedProducts, actualProducts);
        verify(productRepository).findAll();
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        Long productId = 1L;
        Product expectedProduct = new Product(productId, "Test Product", 100, "Test Desc", "test.url", "Test Brand", "Test CarBrand");
        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        // Act
        Product actualProduct = productService.findById(productId);

        // Assert
        assertEquals(expectedProduct, actualProduct);
        verify(productRepository).findById(productId);
    }

    @Test
    void findById_WhenProductNotExists_ShouldThrowApiException() {
        // Arrange
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            productService.findById(productId);
        });

        assertEquals("Product with id " + productId + " not found", exception.getMessage());
        assertEquals("PRODUCT_NOT_FOUND", exception.getErrorCode());
        verify(productRepository).findById(productId);
    }

    @Test
    void deleteById_ShouldCallRepositoryDelete() {
        // Arrange
        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);

        // Act
        productService.deleteById(productId);

        // Assert
        verify(productRepository).deleteById(productId);
    }

    @Test
    void save_ShouldReturnSavedProduct() {
        // Arrange
        Product productToSave = new Product(null, "New Product", 150, "New Desc", "new.url", "New Brand", "New CarBrand");
        Product savedProduct = new Product(1L, "New Product", 150, "New Desc", "new.url", "New Brand", "New CarBrand");
        when(productRepository.save(productToSave)).thenReturn(savedProduct);

        // Act
        Product result = productService.save(productToSave);

        // Assert
        assertEquals(savedProduct, result);
        verify(productRepository).save(productToSave);
    }

    @Test
    void update_WhenProductExists_ShouldUpdateFields() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = new Product(productId, "Old Name", 100, "Old Desc", "old.url", "Old Brand", "Old CarBrand");
        ProductRequestDto updateDto = new ProductRequestDto("New Name", 200, "New Desc", "new.url", "New Brand", "New CarBrand");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        // Act
        Product updatedProduct = productService.update(productId, updateDto);

        // Assert
        assertEquals(updateDto.getName(), updatedProduct.getName());
        assertEquals(updateDto.getPrice(), updatedProduct.getPrice());
        assertEquals(updateDto.getDescription(), updatedProduct.getDescription());
        assertEquals(updateDto.getUrl(), updatedProduct.getUrl());
        assertEquals(updateDto.getBrand(), updatedProduct.getBrand());
        assertEquals(updateDto.getCarBrand(), updatedProduct.getCarBrand());
        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void update_WhenProductNotExists_ShouldThrowRuntimeException() {
        // Arrange
        Long productId = 999L;
        ProductRequestDto updateDto = new ProductRequestDto("Name", 100, "Desc", "url", "Brand", "CarBrand");
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.update(productId, updateDto);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    void update_WithPartialFields_ShouldUpdateOnlyProvidedFields() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = new Product(productId, "Original Name", 100, "Original Desc", "original.url", "Original Brand", "Original CarBrand");
        ProductRequestDto updateDto = new ProductRequestDto(null, 200, null, "updated.url", null, null);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        // Act
        Product updatedProduct = productService.update(productId, updateDto);

        // Assert
        assertEquals("Original Name", updatedProduct.getName()); // Не изменилось
        assertEquals(200, updatedProduct.getPrice()); // Изменилось
        assertEquals("Original Desc", updatedProduct.getDescription()); // Не изменилось
        assertEquals("updated.url", updatedProduct.getUrl()); // Изменилось
        assertEquals("Original Brand", updatedProduct.getBrand()); // Не изменилось
        assertEquals("Original CarBrand", updatedProduct.getCarBrand()); // Не изменилось
        verify(productRepository).findById(productId);
        verify(productRepository).save(existingProduct);
    }
}