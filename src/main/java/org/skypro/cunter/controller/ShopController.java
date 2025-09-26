package org.skypro.cunter.controller;

import org.skypro.cunter.model.article.Article;
import org.skypro.cunter.model.basket.UserBasket;
import org.skypro.cunter.model.product.Product;
import org.skypro.cunter.model.search.SearchResult;
import org.skypro.cunter.service.BasketService;
import org.skypro.cunter.service.SearchService;
import org.skypro.cunter.service.StorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final StorageService storageService;
    private final BasketService basketService;
    private final SearchService searchService;

    public ShopController(StorageService storageService, BasketService basketService, SearchService searchService) {
        this.storageService = storageService;
        this.basketService = basketService;
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

    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        Optional<Product> productOpt = storageService.getProductById(id);
        if (productOpt.isPresent()) {
            basketService.addProductToBasket(id);
            return "Продукт успешно добавлен";
        } else {
            return "Продукт не найден";
        }
    }

    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        return basketService.getUserBasket();
    }
}

