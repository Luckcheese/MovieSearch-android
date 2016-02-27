package com.luckcheese.moviesearch.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.luckcheese.moviesearch.R;
import com.luckcheese.moviesearch.domain.Search;
import com.luckcheese.moviesearch.models.MovieSearchResult;
import com.luckcheese.moviesearch.views.holder.HeaderViewHolder;
import com.luckcheese.moviesearch.views.holder.MovieCardViewHolder;

import java.util.Collections;
import java.util.List;


public class MovieListActivity extends AppCompatActivity implements MovieCardViewHolder.CardListener, Search.SearchListener {

    private boolean mTwoPane;

    private SimpleItemRecyclerViewAdapter adapter;
    private Search currentSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        currentSearch = new Search();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

        findViewById(R.id.movie_list).setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentSearch.initWithQuery(query, MovieListActivity.this);
                currentSearch.nextPage();

                adapter = new SimpleItemRecyclerViewAdapter(MovieListActivity.this, currentSearch.getResult());
                ((RecyclerView) findViewById(R.id.movie_list)).setAdapter(adapter);
                adapter.notifyDataSetChanged();

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu);
        return  true;
    }

    // ----- MovieCardViewHolder.CardListener ------------------------------------------

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

    // ----- Search.SearchListener --------------------------------------------

    @Override
    public void onNextPageSuccess() {
        adapter.notifyDataSetChanged();

        findViewById(R.id.movie_list).setVisibility(View.VISIBLE);
        findViewById(R.id.blankState).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onNextPageError(Throwable t) {
        adapter.setValues(Collections.<MovieSearchResult>emptyList());
        adapter.notifyDataSetChanged();

        findViewById(R.id.movie_list).setVisibility(View.INVISIBLE);
        findViewById(R.id.blankState).setVisibility(View.VISIBLE);

        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    // ----- Related classes --------------------------------------------------

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private LayoutInflater inflater;
        private List<MovieSearchResult> mValues;

        public SimpleItemRecyclerViewAdapter(Context context, List<MovieSearchResult> items) {
            mValues = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                return new HeaderViewHolder(inflater.getContext());
            }
            else {
                View view = inflater.inflate(R.layout.movie_list_content, parent, false);
                return new MovieCardViewHolder(view, MovieListActivity.this);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == 0) {
                ((HeaderViewHolder) holder).setTotalResults(currentSearch.getTotal());
            }
            else {
                ((MovieCardViewHolder) holder).setItem(mValues.get(position));
            }

            if (position == getItemCount() - 1) {
                currentSearch.nextPage();
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? 0 : 1;
        }

        public void setValues(List<MovieSearchResult> values) {
            this.mValues = values;
        }
    }

}
