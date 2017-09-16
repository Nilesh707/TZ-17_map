package com.technozion.tz_17;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.technozion.tz_17.VideoPlayer.VideoAdapter;
import com.technozion.tz_17.VideoPlayer.VideoPlayer;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VideoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setVideosRecyclerView();

    }

    void setVideosRecyclerView(){

        String videoIds[] = ((ApplicationController)getApplication()).videoDb.getVideoIds();
        String titles[] =  ((ApplicationController)getApplication()).videoDb.getVideoTitles();

        VideoAdapter videoAdapter = new VideoAdapter(this, videoIds, titles);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.videoList);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setAdapter(videoAdapter);


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
