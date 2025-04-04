package com.kubik.masterskaya.dto.cart;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kubik.masterskaya.dto.product.ProductResponseDto;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CartItemDto {
    private Long id;
    private ProductResponseDto product;
    private int quantity;
}
