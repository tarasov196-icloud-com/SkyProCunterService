package org.skypro.cunter.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.cunter.model.search.SearchResult;
import org.skypro.cunter.model.search.Searchable;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    private SearchService searchService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        searchService = new SearchService(storageService);
    }

    @Test
    void search_NoObjects_ReturnsEmpty() {
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        Collection<SearchResult> results = searchService.search("любое");

        assertTrue(results.isEmpty(), "Ожидается пустой результат при отсутствии объектов");
    }

    @Test
    void search_ObjectsPresentButNoMatch_ReturnsEmpty() {
        Searchable item1 = mock(Searchable.class);
        when(item1.getSearchText()).thenReturn("Здравствуйте");
        Searchable item2 = mock(Searchable.class);
        when(item2.getSearchText()).thenReturn("Мир");

        when(storageService.getAllSearchables()).thenReturn(Arrays.asList(item1, item2));

        Collection<SearchResult> results = searchService.search("книги");

        assertTrue(results.isEmpty(), "Ожидается пустой результат, если объекты не соответствуют запросу");
    }

    @Test
    void search_ObjectMatching_ReturnsCorrectResult() {
        Searchable item1 = mock(Searchable.class);
        when(item1.getSearchText()).thenReturn("Красивая книга");
        when(item1.getId()).thenReturn(UUID.randomUUID());

        when(storageService.getAllSearchables()).thenReturn(Arrays.asList(item1));

        Collection<SearchResult> results = searchService.search("книга");

        assertFalse(results.isEmpty(), "Должен быть найден подходящий объект");
        SearchResult result = results.iterator().next();
        assertEquals("Красивая книга", result.getName(), "Название результата должно соответствовать");
    }
}