package com.technozion.tz_17.eventCategories;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technozion.tz_17.Events;
import com.technozion.tz_17.R;
import com.technozion.tz_17.VideoPlayer.VideoAdapter;

import java.util.ArrayList;

/**
 * Created by sharda on 31/08/17.
 */

public class EventCategoryAdapter extends RecyclerView.Adapter<EventCategoryViewHolder> {

    Context context;
    ArrayList<EventCategory> eventCategories;

    public EventCategoryAdapter(Context context, ArrayList<EventCategory> eventCategories){
        this.eventCategories = eventCategories;
        this.context = context;
    }

    @Override
    public EventCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new EventCategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventCategoryViewHolder holder, final int position) {
        holder.getTitle().setText(eventCategories.get(position).getTitle());
        holder.getImageView().setImageResource(eventCategories.get(position).getImageResourceId());
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Events.class);
                intent.putExtra("Title", eventCategories.get(position).getTitle());
                String query="select category1,name,id from main_event where category_new="+eventCategories.get(position).getId()+" order by category_new ASC";
                intent.putExtra("category1", query);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventCategories.size();
    }
}
