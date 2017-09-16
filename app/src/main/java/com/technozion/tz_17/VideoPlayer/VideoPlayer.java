package com.technozion.tz_17.VideoPlayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.technozion.tz_17.R;

/**
 * Created by Paras on 21/08/17.
 */

public class VideoPlayer{
    YouTubeThumbnailView youTubeThumbnailView;
    ImageView playButton;
    TextView title;
    CardView titleContainer;
    Context context;
    String videoId;

    public static final int TITLE_GONE = 500;
    public static final int TITLE_SHOWN = 501;

    public VideoPlayer(View itemView, final Context context, final String videoId){
        playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VideoPlayer", "Play button clicked");
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Config.DEVELOPER_KEY, videoId);
                context.startActivity(intent);
            }
        });

        title = (TextView)itemView.findViewById(R.id.title);
        titleContainer = (CardView) itemView.findViewById(R.id.titleContainer);

        this.videoId =  videoId;
        youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        this.context = context;
    }

    public YouTubeThumbnailView getYouTubeThumbnailView() {
        return youTubeThumbnailView;
    }

    public void setYouTubeThumbnailView(YouTubeThumbnailView youTubeThumbnailView) {
        this.youTubeThumbnailView = youTubeThumbnailView;
    }

    public ImageView getPlayButton() {
        return playButton;
    }

    public void setPlayButton(ImageView playButton) {
        this.playButton = playButton;
    }

    public String getTitle() {
        return title.getText().toString();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setTitleVisiblity(int code){
        if(code == TITLE_GONE)
            this.titleContainer.setVisibility(View.GONE);
        else if(code == TITLE_SHOWN)
            this.titleContainer.setVisibility(View.VISIBLE);
    }
}
