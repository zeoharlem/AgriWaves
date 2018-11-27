package com.tosinorojinmi.theophilus.agriwaves.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.tosinorojinmi.theophilus.agriwaves.Activities.VideoPlayerActivity;
import com.tosinorojinmi.theophilus.agriwaves.Models.Episodes;
import com.tosinorojinmi.theophilus.agriwaves.Network.MyVolleySingleton;
import com.tosinorojinmi.theophilus.agriwaves.R;

import java.util.ArrayList;

/**
 * Created by Theophilus on 8/17/2018.
 */

public class SingleEpisodeRecyclerAdapter extends RecyclerView.Adapter<SingleEpisodeRecyclerAdapter.MyEpisodeHolder>{

    private LayoutInflater layoutInflater;
    private MyVolleySingleton volleySingleton;
    private ArrayList<Episodes> episodesArrayList;
    private ImageLoader imageLoader;
    private Context context;
    private MenuBuilder popupMenu;

    public SingleEpisodeRecyclerAdapter(Context context,ArrayList<Episodes> episodesArrayList) {
        this.context            = context;
        this.episodesArrayList  = episodesArrayList;
        layoutInflater          = LayoutInflater.from(context);
        volleySingleton         = MyVolleySingleton.getInstance(context);
        imageLoader             = volleySingleton.getImageLoader();
    }

    @NonNull
    @Override
    public MyEpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = layoutInflater.inflate(R.layout.series_category_item_view, parent, false);
        return new MyEpisodeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyEpisodeHolder holder, int position) {
        final Episodes episodes   = getEpisodesArrayList().get(position);
        holder.textViewHeader.setText(episodes.getSeriesTitle());
        holder.textViewDate.setText(episodes.getDateCreated());

        holder.boxLayVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent   = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("videoId", episodes.getVideoId());
                context.startActivity(intent);
            }
        });

        //Image Loader for Volley
        String imageUrlRow  = episodes.getSeriesImgs();
        if(imageUrlRow != null){
            this.imageLoader.get(imageUrlRow, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.imageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

        //Button Youtube Video Watch
        holder.watchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent   = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("videoId", episodes.getVideoId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return episodesArrayList.size();
    }

    public MyVolleySingleton getVolleySingleton() {
        return volleySingleton;
    }

    public void setVolleySingleton(MyVolleySingleton volleySingleton) {
        this.volleySingleton = volleySingleton;
    }

    public ArrayList<Episodes> getEpisodesArrayList() {
        return episodesArrayList;
    }

    public void setEpisodesArrayList(ArrayList<Episodes> episodesArrayList) {
        this.episodesArrayList = episodesArrayList;
    }

    public void addEpisodeArrayList(Episodes episodes){
        this.episodesArrayList.add(episodes);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class MyEpisodeHolder extends RecyclerView.ViewHolder{
        public TextView textViewHeader, textViewDesc, textViewDate, episodeCounter;
        private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;
        private ImageView imageView, overFlow;
        private Button watchView;
        private LinearLayout boxLayVid;

        public MyEpisodeHolder(View itemView) {
            super(itemView);
            textViewHeader  = itemView.findViewById(R.id.headerText);
            textViewDate    = itemView.findViewById(R.id.date_created);
            imageView       = itemView.findViewById(R.id.image_view);
            watchView       = itemView.findViewById(R.id.watchView);
            boxLayVid       = itemView.findViewById(R.id.boxLay1);
            setTypeFaceTask(itemView);
        }

        private void setTypeFaceTask(View itemView){
            myCustomTypeface        = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/OpenSans-Regular.ttf");
            myCustomTypefaceBold    = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/DaxlinePro-Bold.ttf");
            myCustomTypefaceBlack   = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/OpenSans-ExtraBold.ttf");
            textViewDate.setTypeface(myCustomTypefaceBold);
            textViewHeader.setTypeface(myCustomTypefaceBold);
            watchView.setTypeface(myCustomTypefaceBlack);
        }
    }
}
