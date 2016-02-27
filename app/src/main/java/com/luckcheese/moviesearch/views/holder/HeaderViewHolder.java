package com.luckcheese.moviesearch.views.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.luckcheese.moviesearch.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    public HeaderViewHolder(Context context) {
        super(new TextView(context));
    }

    public void setTotalResults(int results) {
        String disclaimerText = itemView.getContext().getString(R.string.search_total_results, results);

        int boldStart = disclaimerText.indexOf("BB");
        disclaimerText = disclaimerText.replaceFirst("BB", "");
        int boldEnd = disclaimerText.indexOf("BB", boldStart);
        disclaimerText = disclaimerText.replaceFirst("BB", "");

        SpannableStringBuilder str = new SpannableStringBuilder(disclaimerText);
        str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), boldStart, boldEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) itemView).setText(str);
    }
}
