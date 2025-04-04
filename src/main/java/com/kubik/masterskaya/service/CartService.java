package com.kubik.masterskaya.service;

import com.kubik.masterskaya.entity.Cart;
import com.kubik.masterskaya.entity.CartItem;

import java.util.List;

public interface CartService {
    void addItem(Long cartId, CartItem cartItem);

    void removeItem(Long cartId, Long cartItemId);

    List<CartItem> getItems(Long cartId);

    void clean(Long cartId);

    Cart create();

    void increment(Long itemId, Integer incrementCount, Long id);

    Cart getCartById(Long cartId);
}
