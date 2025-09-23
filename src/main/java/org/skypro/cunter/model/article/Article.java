package org.skypro.cunter.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.cunter.model.search.Searchable;
import java.util.UUID;

public class Article implements Searchable {
    private final UUID id;
    private String title;
    private String content;

    public Article(UUID id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    private String contentType;

    @JsonIgnore
    public String getContentType() {
        return contentType;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getSearchText() {
        return title + " " + content;
    }

}