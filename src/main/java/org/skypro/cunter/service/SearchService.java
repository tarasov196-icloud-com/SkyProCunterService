package org.skypro.cunter.service;

import org.skypro.cunter.model.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<SearchResult> search(String query) {
        return storageService.getAllSearchables().stream()
                .filter(item -> item.getSearchText() != null &&
                        item.getSearchText().toLowerCase().contains(query.toLowerCase()))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}
