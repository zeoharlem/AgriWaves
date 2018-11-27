package com.tosinorojinmi.theophilus.agriwaves.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.tosinorojinmi.theophilus.agriwaves.Activities.VideoPlayerActivity;
import com.tosinorojinmi.theophilus.agriwaves.Models.Post;
import com.tosinorojinmi.theophilus.agriwaves.Network.MyVolleySingleton;
import com.tosinorojinmi.theophilus.agriwaves.R;

import java.util.ArrayList;

/**
 * Created by Theophilus on 8/14/2018.
 */

public class RecyclerViewAdapters extends RecyclerView.Adapter<RecyclerViewAdapters.MyPostListViewHolder>{

    private LayoutInflater layoutInflater;
    private MyVolleySingleton volleySingleton;
    private ArrayList<Post> postArrayList;
    private ImageLoader imageLoader;
    private Context context;
    private MenuBuilder popupMenu;

    public RecyclerViewAdapters(Context context, ArrayList<Post> postArrayList) {
        this.context        = context;
        this.postArrayList  = postArrayList;
        layoutInflater      = LayoutInflater.from(context);
        volleySingleton     = MyVolleySingleton.getInstance(context);
        imageLoader         = volleySingleton.getImageLoader();
    }

    @NonNull
    @Override
    public MyPostListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = layoutInflater.inflate(R.layout.single_item_card_view, parent, false);
        return new MyPostListViewHolder(view);
    }

    public void addPostArrayList(Post post){
        this.postArrayList.add(post);
        notifyDataSetChanged();
    }

    public void addArrayListPostRow(ArrayList postArrayList){
        this.postArrayList.addAll(postArrayList);
        notifyDataSetChanged();
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyPostListViewHolder holder, int position) {
        final Post post   = postArrayList.get(position);
        holder.textViewHeader.setText(post.getPostTitle());
        holder.textViewDate.setText(post.getDateCreated());
        holder.textViewDesc.setText(Html.fromHtml(post.getPostDesc()));
        String imageUrlRow  = post.getImageUrl();
        if(imageUrlRow != null){
            this.imageLoader.get(imageUrlRow, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.imageView.setImageBitmap(response.getBitmap());
                    /**
                     * OnclickListener on ImageView
                     */
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent   = new Intent(context, VideoPlayerActivity.class);
                            intent.putExtra("videoId", post.getVideoId());
                            context.startActivity(intent);
                        }
                    });
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        /**
         * OnclickListener on TitleHeader
         */
        holder.textViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent   = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("videoId", post.getVideoId());
                context.startActivity(intent);
            }
        });

        //set the watch vid clickListener
        holder.buttonWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent   = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("videoId", post.getVideoId());
                context.startActivity(intent);
            }
        });

        //set the share vid clickListener
        holder.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareIntentRow(post);
            }
        });

        //set the like vid clickListener
        /*holder.buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

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
                                shareIntentRow(post);
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
    }


    private void shareIntentRow(Post post){
        Intent intent   = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Intent.EXTRA_SUBJECT, post.getPostTitle());
        intent.putExtra(Intent.EXTRA_TEXT, "Just Watched this "+post.getPostTitle()+ " https://www.youtube.com/watch?v="+post.getVideoId());
        context.startActivity(Intent.createChooser(intent, "Shared from AgriwavesTV Mobile App"));
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public ArrayList<Post> getPostArrayList() {
        return postArrayList;
    }

    public void setPostArrayList(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }

    public class MyPostListViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHeader, textViewDesc, textViewDate;
        private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;
        private Button buttonLike, buttonWatch, buttonShare;
        private ImageView imageView, overFlow;

        public MyPostListViewHolder(View itemView) {
            super(itemView);
            textViewHeader  = itemView.findViewById(R.id.headerText);
            textViewDate    = itemView.findViewById(R.id.publishAt);
            imageView       = itemView.findViewById(R.id.image_view);
            overFlow        = itemView.findViewById(R.id.overflow);
            textViewDesc    = itemView.findViewById(R.id.fullDesc);
            //buttonLike      = itemView.findViewById(R.id.favouriteVid);
            buttonShare     = itemView.findViewById(R.id.shareVid);
            buttonWatch     = itemView.findViewById(R.id.watchView);
            setTypeFaceTask(itemView);
        }

        private void setTypeFaceTask(View itemView){
            myCustomTypeface        = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/OpenSans-Regular.ttf");
            myCustomTypefaceBold    = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/DaxlinePro-Bold.ttf");
            myCustomTypefaceBlack   = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/OpenSans-ExtraBold.ttf");
            textViewHeader.setTypeface(myCustomTypefaceBold);
            textViewDesc.setTypeface(myCustomTypeface);
            buttonWatch.setTypeface(myCustomTypefaceBlack);
            buttonShare.setTypeface(myCustomTypefaceBlack);
            //buttonLike.setTypeface(myCustomTypefaceBlack);
        }
    }
}
