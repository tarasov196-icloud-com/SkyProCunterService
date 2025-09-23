package org.skypro.cunter.controller;

import org.skypro.cunter.model.article.Article;
import org.skypro.cunter.model.product.Product;
import org.skypro.cunter.model.search.SearchResult;
import org.skypro.cunter.service.SearchService;
import org.skypro.cunter.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ShopController {
    private final StorageService storageService;

    private final SearchService searchService;

    public ShopController(StorageService storageService, SearchService searchService) {
        this.storageService = storageService;
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public Collection<SearchResult> search(@RequestParam("pattern") String pattern) {
        return searchService.search(pattern);
    }@GetMapping("/product")
    public Collection<Product> getAllProduct() {
        return storageService.getAllProducts();
    }@GetMapping("/article")
    public Collection<Article> getAllArticles() {
        return storageService.getAllArticles();
    }
}

