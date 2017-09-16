package com.technozion.tz_17;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by A on 2/17/2016.
 */
public class Notify_adapter extends RecyclerView.Adapter<Notify_adapter.Notify_holder>  {
    private LayoutInflater inflater;
    List<Notify_list_item> data= Collections.emptyList();
    private Context item_context;

    public Notify_adapter(Context context,List<Notify_list_item> data){
        inflater= LayoutInflater.from(context);
        this.data=data;
        item_context=context;
    }

    @Override
    public Notify_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.notify_list_view , parent, false);
        Notify_holder holder=new Notify_holder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(Notify_holder holder, int position) {
        Notify_list_item current=data.get(position);
        holder.title.setText(current.title);
        holder.text.setText(current.text);
        holder.time.setText(current.time);
        holder.icon.setImageResource(current.icon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class Notify_holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,text,time;
        ImageView icon;

        public Notify_holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title= (TextView) itemView.findViewById(R.id.notify_title);
            text= (TextView) itemView.findViewById(R.id.notify_text);
            icon= (ImageView) itemView.findViewById(R.id.notify_image);
            time= (TextView) itemView.findViewById(R.id.notify_time);
        }

        @Override
        public void onClick(View v) {
            /*Intent I=new Intent(item_context,Class.forName("com.spree.spree_app.Events"));
            I.putExtra("type",title.getText().toString().toLowerCase());
            item_context.startActivity(I);*/
            Toast.makeText(item_context,"under construction",Toast.LENGTH_SHORT).show();
        }
    }
}
