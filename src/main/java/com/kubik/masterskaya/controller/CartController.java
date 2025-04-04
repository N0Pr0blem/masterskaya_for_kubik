package com.kubik.masterskaya.controller;

import com.kubik.masterskaya.dto.cart.CartRequestDto;
import com.kubik.masterskaya.dto.cart.CartResponseDto;
import com.kubik.masterskaya.dto.cart.CartPatchItemDto;
import com.kubik.masterskaya.service.UserCartService;
import com.kubik.masterskaya.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final UserService userService;
    private final UserCartService userCartService;

    @GetMapping
    @Operation(summary = "Get cart of authenticate user")
    public ResponseEntity<CartResponseDto> getCart(Principal principal) {
        return ResponseEntity.ok(userCartService.getCart(userService.getUserByUsername(principal.getName())));
    }

    @PostMapping
    @Operation(summary = "Add to authenticate user's cart product ")
    public ResponseEntity<String> addToCart(@RequestBody CartRequestDto cartRequestDto, Principal principal) {
        userCartService.addToCart(cartRequestDto, userService.getUserByUsername(principal.getName()));
        return ResponseEntity.ok("Product was added to cart");
    }

    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Delete from authenticate user's cart product")
    public ResponseEntity<String> deleteFromCart(@PathVariable Long itemId, Principal principal) {
        userCartService.removeFromCart(itemId, userService.getUserByUsername(principal.getName()));
        return ResponseEntity.ok("Item was deleted from cart");
    }

    @PatchMapping("/items/{itemId}")
    @Operation(summary = "Increment count of product from authenticate user's cart")
    public ResponseEntity<String> incrementItemCount(@PathVariable Long itemId,@RequestBody CartPatchItemDto cartPatchItemDto, Principal principal) {
        userCartService.incrementItemCount(itemId, cartPatchItemDto, userService.getUserByUsername(principal.getName()));
        return ResponseEntity.ok("Item was incremented from cart");
    }

    @DeleteMapping
    @Operation(summary = "Clean authenticate user's cart")
    public ResponseEntity<String> cleanCart(Principal principal) {
        userCartService.cleanCart(userService.getUserByUsername(principal.getName()));
        return ResponseEntity.ok("Cart successfully cleaned");
    }

}
