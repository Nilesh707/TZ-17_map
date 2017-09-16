package com.technozion.tz_17;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by A on 2/13/2016.
 */
public class Adapter_Events  extends RecyclerView.Adapter<Adapter_Events.R_ViewHolder> {


        private LayoutInflater inflater;
        List<Event_list_item> data= Collections.emptyList();
        private Context item_context;

        public Adapter_Events(Context context, List<Event_list_item> data){
            inflater= LayoutInflater.from(context);
            this.data=data;
            item_context=context;
        }
        @Override
        public R_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.card_item, parent, false);
            R_ViewHolder holder=new R_ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(R_ViewHolder holder, int position) {
            Event_list_item current=data.get(position);
            holder.event_type=current.event_type;
            holder.event_id=current.event_id;
            holder.title.setText(current.title);
//            holder.description.setText(current.description);
            holder.icon.setImageResource(item_context.getResources().getIdentifier("id"+current.event_id, "drawable", item_context.getPackageName()));
            int k = item_context.getResources().getIdentifier("id"+current.event_id, "drawable", item_context.getPackageName());
            if(k==0) Events.setImage(k,current.event_id,holder.icon);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class R_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title,description;
            String event_id;
            ImageView icon;
            String event_type;
            public R_ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                title= (TextView) itemView.findViewById(R.id.card_title);
                Typeface face= Typeface.createFromAsset(item_context.getAssets(), "fonts/Teen Bold.ttf");
                title.setTypeface(face);
//                description=(TextView) itemView.findViewById(R.id.card_text);
                icon= (ImageView) itemView.findViewById(R.id.card_icon);
            }

            @Override
            public void onClick(View v) {

                try {
                    //Changing class .forName("com.technozion.tz_16.Event_main") to Test Class
                    Intent I=new Intent(item_context,Class.forName("com.technozion.tz_17.Event_main"));
                    I.putExtra("event_type",event_type);
                    I.putExtra("event_id",event_id);
                    I.putExtra("title", title.getText());
                    I.putExtra("icon_id", "id"+event_id);
//                    I.putExtra("description",description.getText());
                    item_context.startActivity(I);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


}
