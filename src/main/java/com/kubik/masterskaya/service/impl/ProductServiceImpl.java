package com.kubik.masterskaya.service.impl;

import com.kubik.masterskaya.dto.product.ProductRequestDto;
import com.kubik.masterskaya.entity.Product;
import com.kubik.masterskaya.exception.ApiException;
import com.kubik.masterskaya.repository.ProductRepository;
import com.kubik.masterskaya.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ApiException("Product with id "+id+" not found","PRODUCT_NOT_FOUND"));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product save(Product food) {
        return productRepository.save(food);
    }

    @Override
    public Product update(Long id, ProductRequestDto updateDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));

        if (updateDto.getName() != null) product.setName(updateDto.getName());
        if (updateDto.getPrice() != 0) product.setPrice(updateDto.getPrice());
        if (updateDto.getDescription() != null) product.setDescription(updateDto.getDescription());
        if (updateDto.getUrl() != null) product.setUrl(updateDto.getUrl());
        if (updateDto.getBrand() != null) product.setBrand(updateDto.getBrand());
        if (updateDto.getCarBrand() != null) product.setCarBrand(updateDto.getCarBrand());

        return productRepository.save(product);
    }
}
