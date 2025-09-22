package org.skypro.cunter.model.product;

import java.util.UUID;


public class SimpleProduct extends Product {
    private final double price;

    public SimpleProduct(UUID id, String name, double price) {
        super(id, name);
        if (price <= 0) {
            throw new IllegalArgumentException("Цена продукта должна быть строго больше 0");
        }
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f руб.", getName(), price);
    }
    @Override
    public String getSearchTerm() {
        return getName();
    }

    @Override
    public String getContentType() {
        return "PRODUCT";
    }
}