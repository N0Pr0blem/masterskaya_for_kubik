package com.kubik.masterskaya.service.impl;

import com.kubik.masterskaya.dto.cart.CartPatchItemDto;
import com.kubik.masterskaya.dto.cart.CartRequestDto;
import com.kubik.masterskaya.dto.cart.CartResponseDto;
import com.kubik.masterskaya.entity.CartItem;
import com.kubik.masterskaya.entity.User;
import com.kubik.masterskaya.mapper.CartItemMapper;
import com.kubik.masterskaya.service.CartService;
import com.kubik.masterskaya.service.ProductService;
import com.kubik.masterskaya.service.UserCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCartServiceImpl implements UserCartService {
    private final CartService cartService;
    private final CartItemMapper cartItemMapper;
    private final ProductService productService;

    @Override
    public void addToCart(CartRequestDto cartRequestDto, User user) {
        cartService.addItem(user.getCart().getId(), new CartItem().toBuilder()
                .product(productService.findById(cartRequestDto.getProductId()))
                .quantity(cartRequestDto.getCount())
                .cart(user.getCart())
                .build());
    }

    @Override
    public void removeFromCart(Long cartItemId, User user) {
        cartService.removeItem(user.getCart().getId(), cartItemId);
    }

    @Override
    public void cleanCart(User user) {
        cartService.clean(user.getCart().getId());
    }

    @Override
    public CartResponseDto getCart(User user) {
        return new CartResponseDto(user.getCart().getId(), cartItemMapper.toDtos(cartService.getItems(user.getCart().getId())));
    }

    @Override
    public void incrementItemCount(Long itemId, CartPatchItemDto cartPatchItemDto, User user) {
        cartService.increment(itemId,cartPatchItemDto.getIncrementCount(),user.getCart().getId());
    }
}
