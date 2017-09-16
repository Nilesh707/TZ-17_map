package com.technozion.tz_17.recentUpdates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import java.util.ArrayList;

/**
 * Created by sharda on 16/09/17.
 */

public class RecentUpdatesAdapter extends RecyclerView.Adapter<RecentUpdatesViewHolder> {

    Context context;
    ArrayList<RecentUpdatesModel> recentUpdates;

    public RecentUpdatesAdapter(ArrayList<RecentUpdatesModel> recentUpdates, Context context){
        this.recentUpdates = recentUpdates;
        this.context = context;
    }

    @Override
    public RecentUpdatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecentUpdatesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
