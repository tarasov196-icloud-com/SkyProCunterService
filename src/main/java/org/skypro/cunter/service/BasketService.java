package org.skypro.cunter.service;

import org.skypro.cunter.exception.NoSuchProductException;
import org.skypro.cunter.model.basket.BasketItem;
import org.skypro.cunter. model.basket.ProductBasket;
import org.skypro.cunter.model.basket.UserBasket;
import org.skypro.cunter.model.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@SessionScope
@Service
public class BasketService {

    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProductToBasket(UUID id) {
        Optional<Product> productOpt = storageService.getProductById(id);
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Продукт с " + id + " не найден");
        }
        Product product = productOpt.get();
        productBasket.addProduct(product.getId());
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> productMap = productBasket.getProducts();

        List<BasketItem> items = productMap.entrySet().stream()
                .map(entry -> {
                    UUID id = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = storageService.getProductById(id)
                            .orElseThrow(() -> new NoSuchProductException("Продукт не найден: " + id));
                    // Можно удалить строку productBasket.addProduct(product.getId());, так как это лишнее
                    return new BasketItem(product, quantity);
                })
                .collect(Collectors.toList());

        double total = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        return new UserBasket(items, total);
    }

}

