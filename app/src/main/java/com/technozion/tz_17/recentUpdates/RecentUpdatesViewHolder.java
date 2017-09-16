package com.technozion.tz_17.recentUpdates;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.technozion.tz_17.R;


/**
 * Created by sharda on 16/09/17.
 */

public class RecentUpdatesViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public RecentUpdatesViewHolder(View itemView) {
        super(itemView);
        this.textView = (TextView) itemView.findViewById(R.id.textView);
    }
}
