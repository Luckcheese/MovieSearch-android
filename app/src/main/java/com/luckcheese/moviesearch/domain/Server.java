package com.luckcheese.moviesearch.domain;

import com.luckcheese.moviesearch.models.BaseRequestResponse;
import com.luckcheese.moviesearch.models.Movie;
import com.luckcheese.moviesearch.models.MovieSearchResult;
import com.luckcheese.moviesearch.models.SearchResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class Server {

    private ImdbRequests requests;

    public void search(String query, final SearchCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("s", query);
        requests.search(params).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (onRequestSuccess(response, callback)) {
                    callback.setSearchResult(response.body().getSearch());
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                callback.setRequestError(t);
            }
        });
    }

    public void details(String itemId, final DetailsCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("i", itemId);
        requests.details(params).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (onRequestSuccess(response, callback)) {
                    callback.setMovieDetails(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                callback.setRequestError(t);
            }
        });
    }

    private boolean onRequestSuccess(Response<? extends BaseRequestResponse> response, RequestError callback) {
        BaseRequestResponse imdbResponse = response.body();
        if (response.isSuccess()) {
            if (imdbResponse.isSuccess()) {
                return true;
            }
            else {
                callback.setRequestError(new Exception(imdbResponse.getMessage()));
            }
        }
        else {
            try {
                callback.setRequestError(new Exception(response.errorBody().string()));
            } catch (IOException e) {
                callback.setRequestError(e);
            }
        }
        return false;
    }

    // ----- Singleton --------------------------------------------------------

    private static Server sInstance;

    private Server() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requests = retrofit.create(ImdbRequests.class);
    }

    public static Server getInstance() {
        if (sInstance == null) {
            sInstance = new Server();
        }
        return sInstance;
    }

    // ----- Related classes --------------------------------------------------

    public interface RequestError {
        void setRequestError(Throwable t);
    }

    public interface SearchCallback extends RequestError {
        void setSearchResult(List<MovieSearchResult> searchResult);
    }

    public interface DetailsCallback extends RequestError {
        void setMovieDetails(Movie movie);
    }

    public interface ImdbRequests {

        @GET("/")
        Call<SearchResult> search(@QueryMap Map<String, String> params);

        @GET("/")
        Call<Movie> details(@QueryMap Map<String, String> params);
    }
}
