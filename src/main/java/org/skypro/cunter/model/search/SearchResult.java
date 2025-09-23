package org.skypro.cunter.model.search;


public final class SearchResult {
    private final String id;
    private final String name;
    private final String contentType;

    public SearchResult(String id, String name, String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    public static SearchResult fromSearchable(Searchable searchable) {
//        String idString = searchable.getId() != null ? searchable.getId().toString() : null;
//        String name = searchable.getSearchText();
//        String contentType = Searchable.getContentType();
        return new SearchResult(searchable.getId().toString(), searchable.getSearchText(), searchable.getContentType());
    }
}
