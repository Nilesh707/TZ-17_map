package com.technozion.tz_17.VideoPlayer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.technozion.tz_17.R;

/**
 *
 */
public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //these ids are the unique id for each video
    String[] videoID;
    String[] titles;
    Context context;

    public VideoAdapter(Context context, String[] videoID, String[] titles) {
        this.context = context;
        this.videoID = videoID;
        this.titles = titles;
    }

    private class TextHolder extends RecyclerView.ViewHolder{

        public TextHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case 0 :
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_page_title_element, parent, false);
                viewHolder = new TextHolder(itemView);
                break;
            case 1 :
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_item, parent, false);
                viewHolder = new VideoInfoHolder(itemView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        if(viewHolder.getItemViewType() == 0)
            return;

        VideoInfoHolder holder = (VideoInfoHolder) viewHolder;

        holder.getVideoPlayer().setTitle(titles[position]);

        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                //holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        holder.youTubeThumbnailView.initialize(Config.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(videoID[position]);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoID.length;
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;
        TextView title;
        VideoPlayer videoPlayer;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            title =(TextView)itemView.findViewById(R.id.title);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
            videoPlayer = new VideoPlayer(itemView, context, videoID[getAdapterPosition()+1]);
        }

        public VideoPlayer getVideoPlayer() {
            return videoPlayer;
        }

        public void setVideoPlayer(VideoPlayer videoPlayer) {
            this.videoPlayer = videoPlayer;
        }

        @Override
        public void onClick(View v) {

            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Config.DEVELOPER_KEY, videoID[getLayoutPosition()]);
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;
        else return 1;
    }
}