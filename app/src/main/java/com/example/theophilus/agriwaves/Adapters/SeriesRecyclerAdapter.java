package com.example.theophilus.agriwaves.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.theophilus.agriwaves.Activities.SeriesEpisodeActivity;
import com.example.theophilus.agriwaves.Models.Series;
import com.example.theophilus.agriwaves.Network.MyVolleySingleton;
import com.example.theophilus.agriwaves.R;

import java.util.ArrayList;

/**
 * Created by Theophilus on 8/15/2018.
 */

public class SeriesRecyclerAdapter extends RecyclerView.Adapter<SeriesRecyclerAdapter.MySeriesHolder>{

    private LayoutInflater layoutInflater;
    private MyVolleySingleton volleySingleton;
    private ArrayList<Series> seriesArrayList;
    private ImageLoader imageLoader;
    private Context context;
    private MenuBuilder popupMenu;

    public SeriesRecyclerAdapter(Context context, ArrayList<Series> arrayList) {
        this.context            = context;
        this.seriesArrayList    = arrayList;
        layoutInflater          = LayoutInflater.from(context);
        volleySingleton         = MyVolleySingleton.getInstance(context);
        imageLoader             = volleySingleton.getImageLoader();
    }

    @NonNull
    @Override
    public MySeriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = layoutInflater.inflate(R.layout.series_item_view, parent, false);
        return new MySeriesHolder(view);
    }

    public void addBlogArrayList(Series series){
        this.seriesArrayList.add(series);
        notifyDataSetChanged();
    }

    public void addArrayBlogListRow(ArrayList seriesArrayList){
        this.seriesArrayList.addAll(seriesArrayList);
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MySeriesHolder holder, int position) {
        final Series series   = seriesArrayList.get(position);
        holder.textViewHeader.setText(series.getSeriesTitle());
        //holder.textViewDesc.setText(series.getSeriesDesc());
        holder.textViewDate.setText(series.getDateCreated());
        holder.episodeCounter.setText(series.getTotalNumber());

        String imageUrlRow  = series.getSeriesImgs();
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

        holder.overFlow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                popupMenu                   = new MenuBuilder(context);
                MenuInflater menuInflater   = new MenuInflater(context);
                menuInflater.inflate(R.menu.option_menu, popupMenu);

                @SuppressLint("RestrictedApi")
                MenuPopupHelper menuPopupHelper = new MenuPopupHelper(context, popupMenu, holder.overFlow);
                menuPopupHelper.setForceShowIcon(true);

                popupMenu.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.watchLater:
                                return true;
                            case R.id.shareNow:
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {

                    }

                });

                menuPopupHelper.show();
            }
        });

        holder.otherEpisodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent   = new Intent(context, SeriesEpisodeActivity.class);
                intent.putExtra("series_cat_id", series.getSeriesId());
                intent.putExtra("series_title", series.getSeriesTitle());
                intent.putExtra("series_video_url", series.getSeriesImgs());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seriesArrayList.size();
    }

    public ArrayList<Series> getSeriesArrayList() {
        return seriesArrayList;
    }

    public void setSeriesArrayList(ArrayList<Series> seriesArrayList) {
        this.seriesArrayList = seriesArrayList;
    }

    public void addSeriesArrayList(Series series){
        this.seriesArrayList.add(series);
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class MySeriesHolder extends RecyclerView.ViewHolder{
        public TextView textViewHeader, textViewDesc, textViewDate, episodeCounter;
        private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;
        private ImageView imageView, overFlow;
        private LinearLayout otherEpisodes;

        public MySeriesHolder(View itemView) {
            super(itemView);
            textViewHeader  = itemView.findViewById(R.id.headerText);
            textViewDate    = itemView.findViewById(R.id.date_created);
            episodeCounter  = itemView.findViewById(R.id.episode_counts);
            imageView       = itemView.findViewById(R.id.image_view);
            overFlow        = itemView.findViewById(R.id.overflow);
            otherEpisodes   = itemView.findViewById(R.id.episodeVids);
            setTypeFaceTask(itemView);
        }

        private void setTypeFaceTask(View itemView){
            myCustomTypeface        = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/OpenSans-Regular.ttf");
            myCustomTypefaceBold    = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/DaxlinePro-Bold.ttf");
            myCustomTypefaceBlack   = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/OpenSans-ExtraBold.ttf");
            textViewDate.setTypeface(myCustomTypefaceBold);
            textViewHeader.setTypeface(myCustomTypefaceBold);
            episodeCounter.setTypeface(myCustomTypefaceBlack);
        }
    }
}
