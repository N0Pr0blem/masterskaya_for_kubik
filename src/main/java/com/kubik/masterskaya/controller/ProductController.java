package com.kubik.masterskaya.controller;

import com.kubik.masterskaya.dto.product.ProductRequestDto;
import com.kubik.masterskaya.dto.product.ProductResponseDto;
import com.kubik.masterskaya.entity.Product;
import com.kubik.masterskaya.mapper.ProductMapper;
import com.kubik.masterskaya.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping()
    @Operation(summary = "Get all products")
    public ResponseEntity<List<ProductResponseDto>> getAll() {
        return ResponseEntity.ok(productMapper.toDtos(productService.findAll()));
    }

    @PostMapping()
    @Operation(summary = "Add product")
    public ResponseEntity<ProductResponseDto> addFood(@RequestBody ProductResponseDto foodDto) {
        Product product = productMapper.toEntity(foodDto);
        return ResponseEntity.ok(productMapper.toDto(productService.save(product)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    public ResponseEntity<String> deleteFood(@PathVariable long id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Food successfully deleted");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update product by id")
    public ResponseEntity<ProductResponseDto> updateFood(@PathVariable(name = "id") long id, @RequestBody ProductRequestDto updateDto) {
        return ResponseEntity.ok(productMapper.toDto(productService.update(id,updateDto)));
    }
}
