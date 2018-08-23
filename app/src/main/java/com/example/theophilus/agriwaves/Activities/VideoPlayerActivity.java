package com.example.theophilus.agriwaves.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.theophilus.agriwaves.R;
import com.example.theophilus.agriwaves.Utils.DeveloperKeys;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayerActivity extends YouTubeBaseActivity {

    private String videoId;
    private YouTubePlayer mYouTubePlayer;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoId = getIntent().getStringExtra("videoId");
        youTubePlayerView       = findViewById(R.id.youtubePlayerView);
        onInitializedListener   = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setFullscreen(true);
                youTubePlayer.loadVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize(DeveloperKeys.getApiKey(), onInitializedListener);

    }
}
