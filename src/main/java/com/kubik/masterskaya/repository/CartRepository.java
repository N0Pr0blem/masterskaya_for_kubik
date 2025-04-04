package com.kubik.masterskaya.repository;

import com.kubik.masterskaya.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
