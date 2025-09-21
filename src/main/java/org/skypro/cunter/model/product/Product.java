package org.skypro.cunter.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.cunter.model.search.Searchable;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private String name;
    private double price;

    public Product(UUID id, String name, double price) {
        if (id == null) {
            throw new IllegalArgumentException("id не может быть null");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name не может быть null или пустым");
        }
        if (price < 0) {
            throw new IllegalArgumentException("price не может быть отрицательной");
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getSearchText() {
        return name;
    }

    public String getName() {
        return name;
    }

    private String searchTerm;

    @JsonIgnore
    public String getSearchTerm() {
        return searchTerm;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name не может быть null или пустым");
        }
        this.name = name;
    }


    public abstract double getPrice();


    protected double getBasePrice() {
        return price;
    }


    protected void setBasePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("price не может быть отрицательной");
        }
        this.price = price;
    }
}