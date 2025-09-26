package org.skypro.cunter.model.basket;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Component
@SessionScope
public class ProductBasket {
    private final Map<UUID, Integer> products = new HashMap<>();
    private final List<Runnable> listeners = new ArrayList<>();

    public void addProduct(UUID id) {
        products.put(id, products.getOrDefault(id, 0) + 1);
        notifyListeners();
    }

    public Map<UUID, Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    public void addChangeListener(Runnable listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}