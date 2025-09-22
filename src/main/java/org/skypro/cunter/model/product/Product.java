package org.skypro.cunter.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.cunter.model.search.Searchable;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private String name;


    public Product(UUID id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("id не может быть null");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name не может быть null или пустым");
        }
        this.id = id;
        this.name = name;

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

}