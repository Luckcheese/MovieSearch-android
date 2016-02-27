package com.luckcheese.moviesearch.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class SearchResult extends BaseRequestResponse {
    @SerializedName("Search")
    private List<MovieSearchResult> search;
    private int totalResults;

    public List<MovieSearchResult> getSearch() {
        return search;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
