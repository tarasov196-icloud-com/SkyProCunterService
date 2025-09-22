package org.skypro.cunter.model.product;

import java.util.UUID;


public class DiscountProduct extends Product {
    private final double basePrice;
    private final double discountPercent;

    public DiscountProduct(UUID id, String name, double basePrice, double discountPercent) {
        super(id, name);

        if (basePrice < 0) {
            throw new IllegalArgumentException("Базовая цена продукта не может быть отрицательной");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Процент скидки должен быть в диапазоне от 0 до 100 включительно");
        }
        this.basePrice = basePrice;
        this.discountPercent = discountPercent;
    }

    @Override
    public double getPrice() {
        return basePrice * (100 - discountPercent) / 100.0;
    }

    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f (%.1f%% скидка)", getName(), getPrice(), discountPercent);
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