package com.luckcheese.moviesearch.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.luckcheese.moviesearch.R;
import com.luckcheese.moviesearch.domain.FakeServer;
import com.luckcheese.moviesearch.models.MovieSearchResult;
import com.luckcheese.moviesearch.views.holder.ViewHolder;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements ViewHolder.CardListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.movie_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, FakeServer.search()));
    }

    // ----- ViewHolder.CardListener ------------------------------------------

    @Override
    public void onDetailsRequested(MovieSearchResult movie) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(MovieDetailFragment.ARG_ITEM_ID, movie.getImdbID());
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, movie.getImdbID());
            startActivity(intent);
        }
    }

    @Override
    public void onShareRequested(MovieSearchResult movie) {
        Toast.makeText(this, getString(R.string.share_not_supported, movie.getTitle()), Toast.LENGTH_SHORT).show();
    }

    // ----- Related classes --------------------------------------------------

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private LayoutInflater inflater;
        private final List<MovieSearchResult> mValues;

        public SimpleItemRecyclerViewAdapter(Context context, List<MovieSearchResult> items) {
            mValues = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.movie_list_content, parent, false);
            return new ViewHolder(view, MovieListActivity.this);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.setItem(mValues.get(position));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
