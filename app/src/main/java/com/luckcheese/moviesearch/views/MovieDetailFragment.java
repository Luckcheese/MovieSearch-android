package com.luckcheese.moviesearch.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luckcheese.moviesearch.R;
import com.luckcheese.moviesearch.domain.FakeServer;
import com.luckcheese.moviesearch.models.Movie;

public class MovieDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Movie mItem;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = FakeServer.details(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getTitle());
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        populateView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_detail, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.share:
                Toast.makeText(getActivity(), getString(R.string.share_not_supported, mItem.getTitle()), Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateView() {
        View fragView = getView();

        ((TextView) fragView.findViewById(R.id.plot)).setText(mItem.getPlot());

        ((MovieInfoRow) fragView.findViewById(R.id.actors)).setInfo(mItem.getActors());
        ((MovieInfoRow) fragView.findViewById(R.id.directors)).setInfo(mItem.getDirector());
        ((MovieInfoRow) fragView.findViewById(R.id.writers)).setInfo(mItem.getWriter());
        ((MovieInfoRow) fragView.findViewById(R.id.releaseDate)).setInfo(mItem.getReleased());
        ((MovieInfoRow) fragView.findViewById(R.id.duration)).setInfo(mItem.getRuntime());
        ((MovieInfoRow) fragView.findViewById(R.id.genre)).setInfo(mItem.getGenre());
        ((MovieInfoRow) fragView.findViewById(R.id.metascore)).setInfo(mItem.getMetascore());
        ((MovieInfoRow) fragView.findViewById(R.id.awards)).setInfo(mItem.getAwards());
        ((MovieInfoRow) fragView.findViewById(R.id.country)).setInfo(mItem.getCountry());
    }
}
