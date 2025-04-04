package com.kubik.masterskaya.repository;

import com.kubik.masterskaya.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
