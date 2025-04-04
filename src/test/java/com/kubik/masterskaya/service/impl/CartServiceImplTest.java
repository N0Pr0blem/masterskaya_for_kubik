package com.kubik.masterskaya.service.impl;

import com.kubik.masterskaya.entity.Cart;
import com.kubik.masterskaya.entity.CartItem;
import com.kubik.masterskaya.exception.ApiException;
import com.kubik.masterskaya.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void create_ShouldReturnNewCart() {
        Cart expectedCart = new Cart();
        when(cartRepository.save(any(Cart.class))).thenReturn(expectedCart);

        Cart result = cartService.create();

        assertNotNull(result);
        assertEquals(expectedCart, result);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void getCartById_WhenCartExists_ShouldReturnCart() {
        Long cartId = 1L;
        Cart expectedCart = new Cart();
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(expectedCart));

        Cart result = cartService.getCartById(cartId);

        assertEquals(expectedCart, result);
        verify(cartRepository).findById(cartId);
    }

    @Test
    void getCartById_WhenCartNotExists_ShouldThrowApiException() {
        Long cartId = 1L;
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            cartService.getCartById(cartId);
        });

        assertEquals("No such cart exception", exception.getMessage());
        assertEquals("NO_SUCH_CART", exception.getErrorCode());
        verify(cartRepository).findById(cartId);
    }

    @Test
    void addItem_ShouldAddItemToCart() {
        Long cartId = 1L;
        CartItem newItem = new CartItem();
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        cartService.addItem(cartId, newItem);

        assertTrue(cart.getItems().contains(newItem));
        verify(cartRepository).findById(cartId);
        verify(cartRepository).save(cart);
    }

    @Test
    void removeItem_ShouldRemoveItemFromCart() {
        Long cartId = 1L;
        Long itemId = 10L;
        CartItem item = new CartItem();
        item.setId(itemId);

        List<CartItem> items = new ArrayList<>();
        items.add(item);

        Cart cart = new Cart();
        cart.setItems(items);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        cartService.removeItem(cartId, itemId);

        assertFalse(cart.getItems().contains(item));
        verify(cartRepository).findById(cartId);
        verify(cartRepository).save(cart);
    }

    @Test
    void getItems_ShouldReturnCartItems() {
        Long cartId = 1L;
        List<CartItem> expectedItems = List.of(new CartItem(), new CartItem());
        Cart cart = new Cart();
        cart.setItems(expectedItems);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        List<CartItem> result = cartService.getItems(cartId);

        assertEquals(expectedItems, result);
        verify(cartRepository).findById(cartId);
    }

    @Test
    void increment_ShouldIncreaseQuantity() {
        Long cartId = 1L;
        Long itemId = 10L;
        int increment = 2;

        CartItem item = new CartItem();
        item.setId(itemId);
        item.setQuantity(1);

        List<CartItem> items = new ArrayList<>();
        items.add(item);

        Cart cart = new Cart();
        cart.setItems(items);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        cartService.increment(itemId, increment, cartId);

        assertEquals(3, item.getQuantity());
        verify(cartRepository).findById(cartId);
        verify(cartRepository).save(cart);
    }

    @Test
    void increment_WhenResultLessThanOne_ShouldSetToOne() {
        Long cartId = 1L;
        Long itemId = 10L;
        int increment = -5; // приведет к отрицательному количеству

        CartItem item = new CartItem();
        item.setId(itemId);
        item.setQuantity(3);

        List<CartItem> items = new ArrayList<>();
        items.add(item);

        Cart cart = new Cart();
        cart.setItems(items);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        cartService.increment(itemId, increment, cartId);

        assertEquals(1, item.getQuantity());
        verify(cartRepository).findById(cartId);
        verify(cartRepository).save(cart);
    }
}