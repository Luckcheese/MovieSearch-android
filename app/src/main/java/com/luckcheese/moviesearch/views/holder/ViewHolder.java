package com.luckcheese.moviesearch.views.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luckcheese.moviesearch.R;
import com.luckcheese.moviesearch.models.MovieSearchResult;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private MovieSearchResult movie;
    private CardListener listener;

    private ImageView posterView;
    private TextView titleView;
    private TextView plotView;

    public ViewHolder(View view) {
        super(view);

        posterView = (ImageView) view.findViewById(R.id.poster);
        titleView = (TextView) view.findViewById(R.id.title);
        plotView = (TextView) view.findViewById(R.id.plot);

        view.findViewById(R.id.details).setOnClickListener(this);
        view.findViewById(R.id.share).setOnClickListener(this);
    }

    public void setItem(MovieSearchResult movie) {
        this.movie = movie;
        populateView();
    }

    private void populateView() {
        titleView.setText(movie.getTitle());
        plotView.setText(movie.getYear());
    }

    // ----- View.OnClickListener ---------------------------------------------

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (listener != null) {
            switch (viewId) {
                case R.id.details:
                    listener.onDetailsRequested(movie);
                    break;

                case R.id.share:
                    listener.onShareRequested(movie);
                    break;
            }
        }
    }

    // ----- Related classes --------------------------------------------------

    public interface CardListener {
        void onDetailsRequested(MovieSearchResult movie);
        void onShareRequested(MovieSearchResult movie);
    }
}
