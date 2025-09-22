package org.skypro.cunter.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private static final double FIXED_PRICE = 99.0;

    public FixPriceProduct(UUID id, String name) {
        super(id, name);
    }

    @Override
    public double getPrice() {
        return FIXED_PRICE;
    }

    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s: Фиксированная цена %.2f", getName(), FIXED_PRICE);
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