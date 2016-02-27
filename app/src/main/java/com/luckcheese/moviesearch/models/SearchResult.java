package com.luckcheese.moviesearch.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public final class SearchResult implements Serializable {
    @SerializedName("Search")
    private List<MovieSearchResult> search;

    public List<MovieSearchResult> getSearch() {
        return search;
    }
}
