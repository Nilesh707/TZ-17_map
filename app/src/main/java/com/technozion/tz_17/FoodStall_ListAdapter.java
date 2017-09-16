package com.technozion.tz_17;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rishu Kumar on 26/08/2017.
 */

public class FoodStall_ListAdapter extends BaseAdapter {

    private Context mContext;
    private List<FoodStall> mStallList;

    public FoodStall_ListAdapter(Context mContext, List<FoodStall> mStallList) {
        this.mContext = mContext;
        this.mStallList = mStallList;
    }

    @Override
    public int getCount() {
        return mStallList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStallList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mStallList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.list_food_stall, null);
        TextView tvName = (TextView)v.findViewById(R.id.tv_stall_name);
        TextView tvMenu = (TextView) v.findViewById(R.id.stall_menu);
        TextView tvTime = (TextView)v.findViewById(R.id.tv_stall_time);
        TextView tvLocation = (TextView)v.findViewById(R.id.tv_stall_location);
        TextView menu_btn = (TextView) v.findViewById(R.id.menu);
        final ImageButton imgbt_menu = (ImageButton)v.findViewById(R.id.img_bt_stall_menu);
        ImageView stall_img = (ImageView) v.findViewById(R.id.imageView);
        final TextView stall_menu = (TextView)v.findViewById(R.id.stall_menu);
        tvMenu.setText(String.valueOf(mStallList.get(position).getMenu()));
        tvTime.setText(String.valueOf(mStallList.get(position).getTime()));
        tvLocation.setText(mStallList.get(position).getLocation());
        switch (mStallList.get(position).getImage()){
            case 1: stall_img.setImageResource(R.drawable.dominos);
                break;
            case 2: stall_img.setImageResource(R.drawable.redbull);
                break;
            case 3: stall_img.setImageResource(R.drawable.cocacola);
                break;
            case 4: stall_img.setImageResource(R.drawable.amul);
                break;
            case 5: stall_img.setImageResource(R.drawable.kwality_walls);
                break;
            case 6: stall_img.setImageResource(R.drawable.maggi);
                break;
            case 0: stall_img.setVisibility(View.INVISIBLE);
                tvName.setText(mStallList.get(position).getName());
                tvName.setVisibility(View.VISIBLE);
                break;
        }
        stall_menu.setVisibility(View.GONE);
        imgbt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stall_menu.getVisibility()==View.GONE) {
                    imgbt_menu.setImageResource(R.drawable.stall_menu_down);
                    stall_menu.setVisibility(View.VISIBLE);
                }
                else {
                    imgbt_menu.setImageResource(R.drawable.stall_menu_left);
                    stall_menu.setVisibility(View.GONE);
                }
            }
        });
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stall_menu.getVisibility()==View.GONE) {
                    imgbt_menu.setImageResource(R.drawable.stall_menu_down);
                    stall_menu.setVisibility(View.VISIBLE);
                }
                else {
                    imgbt_menu.setImageResource(R.drawable.stall_menu_left);
                    stall_menu.setVisibility(View.GONE);
                }
            }
        });
        return v;
    }
}
