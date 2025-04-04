package com.kubik.masterskaya.service.impl;

import com.kubik.masterskaya.dto.cart.CartResponseDto;
import com.kubik.masterskaya.entity.Cart;
import com.kubik.masterskaya.entity.CartItem;
import com.kubik.masterskaya.exception.ApiException;
import com.kubik.masterskaya.repository.CartRepository;
import com.kubik.masterskaya.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public void addItem(Long cartId, CartItem cartItem) {
        Cart cart = getCartById(cartId);
        cart.getItems().add(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItem(Long cartId, Long cartItemId) {
        Cart cart = getCartById(cartId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .get();
        cart.getItems().remove(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public List<CartItem> getItems(Long cartId) {
        return getCartById(cartId).getItems();
    }

    @Override
    public void clean(Long cartId) {
        Cart cart = getCartById(cartId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public Cart create() {
        return cartRepository.save(new Cart());
    }

    @Override
    public void increment(Long cartItemId, Integer incrementCount, Long cartId) {
        Cart cart = getCartById(cartId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .get();
        cartItem.setQuantity(cartItem.getQuantity()+incrementCount);
        if(cartItem.getQuantity()<1) cartItem.setQuantity(1);
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ApiException("No such cart exception", "NO_SUCH_CART"));
    }
}
