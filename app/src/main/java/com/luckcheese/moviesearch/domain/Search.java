package com.luckcheese.moviesearch.domain;

import com.luckcheese.moviesearch.models.MovieSearchResult;
import com.luckcheese.moviesearch.models.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class Search {
    private static final int PAGE_SIZE = 10; // fixed by api

    private String query;
    private int totalResults;
    private int currentPage;
    private List<MovieSearchResult> result;

    private SearchListener listener;

    public void initWithQuery(String newQuery, SearchListener listener) {
        query = newQuery;
        totalResults = 0;
        currentPage = 0;
        result = new ArrayList<>();

        this.listener = listener;
    }

    public void nextPage() {
        if (!canRequestNextPage()) {
            return;
        }

        currentPage++;
        Server.getInstance().search(query, currentPage, new Server.SearchCallback() {
            @Override
            public void setSearchResult(SearchResult searchResult) {
                totalResults = searchResult.getTotalResults();
                result.addAll(searchResult.getSearch());

                listener.onNextPageSuccess();
            }

            @Override
            public void setRequestError(Throwable t) {
                listener.onNextPageError(t);
            }
        });
    }

    private boolean canRequestNextPage() {
        return currentPage == 0 || (totalResults > currentPage * PAGE_SIZE);
    }

    public List<MovieSearchResult> getResult() {
        return result;
    }

    public int getTotal() {
        return totalResults;
    }

    // ----- Related classes --------------------------------------------------

    public interface SearchListener {
        void onNextPageSuccess();
        void onNextPageError(Throwable t);
    }
}
