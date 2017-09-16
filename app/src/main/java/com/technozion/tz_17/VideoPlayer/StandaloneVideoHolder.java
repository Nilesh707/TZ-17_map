package com.technozion.tz_17.VideoPlayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.technozion.tz_17.R;

/**
 * Created by sharda on 21/08/17.
 */

public class StandaloneVideoHolder {

    VideoPlayer videoPlayer;
    String videoId;
    Context context;

    public StandaloneVideoHolder(View itemView, Context context, final String videoId){

        final String TAG = "StandaloneVideoHolder";
        videoPlayer = new VideoPlayer(itemView, context, videoId);
        Log.d(TAG,"Created");
        this.videoId = videoId;
        this.context = context;
    }

    public void init(){
        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                Log.d("StandaloneVideoHolder", "onThumbnailError - " + errorReason.name());
            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                //holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        videoPlayer.getYouTubeThumbnailView().initialize(Config.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(videoId);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
                Log.d("StandaloneVideoHolder", "onInitializationFailure - " + youTubeInitializationResult.name());
            }
        });

    }

    public VideoPlayer getVideoPlayer() {
        return videoPlayer;
    }

}
