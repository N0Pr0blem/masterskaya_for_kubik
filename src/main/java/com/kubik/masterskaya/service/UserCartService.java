package com.kubik.masterskaya.service;

import com.kubik.masterskaya.dto.cart.CartPatchItemDto;
import com.kubik.masterskaya.dto.cart.CartRequestDto;
import com.kubik.masterskaya.dto.cart.CartResponseDto;
import com.kubik.masterskaya.entity.User;

public interface UserCartService {
    void addToCart(CartRequestDto cartRequestDto,User user);

    void removeFromCart(Long cartItemId,User user);

    void cleanCart(User user);

    CartResponseDto getCart(User user);

    void incrementItemCount(Long itemId, CartPatchItemDto cartPatchItemDto, User userByUsername);
}
