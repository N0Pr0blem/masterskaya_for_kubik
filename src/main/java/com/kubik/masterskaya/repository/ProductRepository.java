package com.kubik.masterskaya.repository;

import com.kubik.masterskaya.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
