package com.kubik.masterskaya.mapper;

import com.kubik.masterskaya.dto.cart.CartItemDto;
import com.kubik.masterskaya.entity.CartItem;
import com.kubik.masterskaya.mapper.base.Mappable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper extends Mappable<CartItem, CartItemDto> {
}
