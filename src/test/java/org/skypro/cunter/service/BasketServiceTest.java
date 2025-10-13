package org.skypro.cunter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.cunter.model.basket.ProductBasket;
import org.skypro.cunter.model.basket.BasketItem;
import org.skypro.cunter.model.basket.UserBasket;
import org.skypro.cunter.model.product.SimpleProduct;
import org.skypro.cunter.model.product.Product;

import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    private BasketService basketService;

    @BeforeEach
    void setUp() {
        basketService = new BasketService(productBasket, storageService);
    }


    @Test
    void addNonExistingProduct_ShouldThrow() {
        UUID productId = UUID.randomUUID();

        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            basketService.addProductToBasket(productId);
        });

        verify(storageService).getProductById(productId);
        verify(productBasket, never()).addProduct(any());
    }


    @Test
    void addExistingProduct_ShouldCallAddProduct() {
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct(productId, "Item", 10.0);

        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        basketService.addProductToBasket(productId);

        verify(storageService).getProductById(productId);
        verify(productBasket).addProduct(product.getId());
    }


    @Test
    void getUserBasket_WhenEmpty_ShouldReturnEmpty() {
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());

        UserBasket basket = basketService.getUserBasket();

        assertNotNull(basket);
        assertTrue(basket.getItems().isEmpty());
        assertEquals(0.0, basket.getTotal());
    }


    @Test
    void getUserBasket_WithItems_ShouldReturnCorrectBasket() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();


        Product product1 = new SimpleProduct(productId1, "Item 1", 20.0);
        Product product2 = new SimpleProduct(productId2, "Item 2", 30.0);


        Map<UUID, Integer> productsMap = new HashMap<>();
        productsMap.put(productId1, 2);
        productsMap.put(productId2, 3);

        when(productBasket.getProducts()).thenReturn(productsMap);
        when(storageService.getProductById(productId1)).thenReturn(Optional.of(product1));
        when(storageService.getProductById(productId2)).thenReturn(Optional.of(product2));

        UserBasket basket = basketService.getUserBasket();

        assertNotNull(basket);
        assertEquals(2, basket.getItems().size());


        Optional<BasketItem> item1Opt = basket.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId1))
                .findFirst();
        Optional<BasketItem> item2Opt = basket.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId2))
                .findFirst();

        assertTrue(item1Opt.isPresent());
        assertTrue(item2Opt.isPresent());

        BasketItem item1 = item1Opt.get();
        BasketItem item2 = item2Opt.get();

        assertEquals(2, item1.getQuantity());
        assertEquals(3, item2.getQuantity());

        double expectedTotal = (20.0 * 2) + (30.0 * 3); // 130.0
        assertEquals(expectedTotal, basket.getTotal());
    }
}