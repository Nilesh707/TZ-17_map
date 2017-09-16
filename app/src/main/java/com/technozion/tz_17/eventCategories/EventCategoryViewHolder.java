package com.technozion.tz_17.eventCategories;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.technozion.tz_17.R;

/**
 * Created by sharda on 31/08/17.
 */

public class EventCategoryViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView title;
    View itemView;

    public EventCategoryViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        this.imageView = (ImageView) itemView.findViewById(R.id.card_icon);
        this.title = (TextView) itemView.findViewById(R.id.card_title);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }
}
