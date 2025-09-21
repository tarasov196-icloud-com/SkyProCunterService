package org.skypro.cunter.service;

import org.skypro.cunter.model.article.Article;
import org.skypro.cunter.model.product.DiscountProduct;
import org.skypro.cunter.model.product.Product;
import org.skypro.cunter.model.product.SimpleProduct;
import org.skypro.cunter.model.search.Searchable;
//import some.package.Searchable;
//import some.package.Article;
//import some.package.Product;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class StorageService {
    private final Map<UUID, Product> productStorage;
    private final Map<UUID, Article> articleStorage;

    public StorageService(Map<UUID, Product> productStorage, Map<UUID, Article> articleStorage) {
        this.productStorage = new HashMap<>();
        this.articleStorage = new HashMap<>();
        bdArticle();
        bdProduct();
    }

    public void bdArticle() {
        Stream.of(
                new Article(UUID.randomUUID(), "Пирожки", "Рецепты пирожков"),
                new Article(UUID.randomUUID(), "На дне", "Школьная литература")
        ).forEach(article -> articleStorage.put(article.getId(),article));
    }public void bdProduct() {
        Stream.of(
                new SimpleProduct(UUID.randomUUID(), "Детская книжка", 100),
                new DiscountProduct(UUID.randomUUID(), "Карандаши", 120, 10)
        ).forEach(product -> productStorage.put(product.getId(),product));
    }

    public Collection<Article> getAllArticles() {

        return articleStorage.values();
    }

    public Collection<Product> getAllProducts() {

        return productStorage.values();
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> combined = new ArrayList<>();
        combined.addAll(getAllArticles());
        combined.addAll(getAllProducts());
        return combined;
    }
}
