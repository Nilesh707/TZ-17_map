package com.technozion.tz_17;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.technozion.tz_17.recentUpdates.RecentUpdatesAdapter;
import com.technozion.tz_17.recentUpdates.RecentUpdatesModel;

import java.util.ArrayList;

public class RecentUpdates extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<RecentUpdatesModel> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_updates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //data = recentUpdatesDbHelper.getUpdates();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecentUpdatesAdapter adapter = new RecentUpdatesAdapter(data, this);
        recyclerView.setAdapter(adapter);

    }

}
