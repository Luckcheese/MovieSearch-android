package com.luckcheese.moviesearch.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.luckcheese.moviesearch.R;

public class MovieInfoRow extends FrameLayout {
    public MovieInfoRow(Context context) {
        super(context);
        init();
    }

    public MovieInfoRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        populateFromXml(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MovieInfoRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        populateFromXml(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MovieInfoRow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        populateFromXml(attrs);
    }

    private void init() {
        View.inflate(getContext(), R.layout.movie_info_row, this);
    }

    private void populateFromXml(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MovieInfoRow,
                0, 0);

        try {
            int imageRes = a.getResourceId(R.styleable.MovieInfoRow_infoImage, R.mipmap.ic_launcher);
            setImage(imageRes);

            if (isInEditMode()) {
                setInfo("Fake text to show on layout preview");
            }
        } finally {
            a.recycle();
        }
    }

    public void setImage(int imageRes) {
        ((ImageView) findViewById(R.id.image)).setImageResource(imageRes);
    }

    public void setInfo(String info) {
        ((TextView) findViewById(R.id.info)).setText(info);
    }
}
