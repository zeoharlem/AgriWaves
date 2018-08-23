package com.example.theophilus.agriwaves.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.theophilus.agriwaves.Models.Blog;
import com.example.theophilus.agriwaves.Network.MyVolleySingleton;
import com.example.theophilus.agriwaves.R;

import java.util.ArrayList;

/**
 * Created by Theophilus on 8/23/2018.
 */

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.BlogListViewHolder>{
    private LayoutInflater layoutInflater;
    private MyVolleySingleton volleySingleton;
    private ArrayList<Blog> blogArrayList;
    private ImageLoader imageLoader;
    private MenuBuilder popupMenu;
    private Context context;

    public BlogRecyclerAdapter(Context context, ArrayList<Blog> blogArrayList) {
        this.context        = context;
        this.blogArrayList  = blogArrayList;
        layoutInflater      = LayoutInflater.from(context);
        volleySingleton     = MyVolleySingleton.getInstance(context);
        imageLoader         = volleySingleton.getImageLoader();
    }

    @NonNull
    @Override
    public BlogListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = layoutInflater.inflate(R.layout.blog_item_card_view, parent, false);
        return new BlogListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BlogListViewHolder holder, int position) {
        final Blog post     = blogArrayList.get(position);
        holder.textViewHeader.setText(post.getPostTitle());
        holder.textViewDesc.setText(post.getPostExcerpt());
        holder.textViewDate.setText(post.getPostDate());
        String imageUrlRow  = post.getMidImage();
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

        //set the share vid clickListener
        holder.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //set the like vid clickListener
        holder.buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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
    }

    @Override
    public int getItemCount() {
        return blogArrayList.size();
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public MyVolleySingleton getVolleySingleton() {
        return volleySingleton;
    }

    public void setVolleySingleton(MyVolleySingleton volleySingleton) {
        this.volleySingleton = volleySingleton;
    }

    public ArrayList<Blog> getBlogArrayList() {
        return blogArrayList;
    }

    public void setBlogArrayList(ArrayList<Blog> blogArrayList) {
        this.blogArrayList = blogArrayList;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public MenuBuilder getPopupMenu() {
        return popupMenu;
    }

    public void setPopupMenu(MenuBuilder popupMenu) {
        this.popupMenu = popupMenu;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void addBlogArrayList(Blog blog){
        this.blogArrayList.add(blog);
        notifyDataSetChanged();
    }

    public class BlogListViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHeader, textViewDesc, textViewDate;
        private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;
        private Button buttonLike, buttonWatch, buttonShare;
        private ImageView imageView, overFlow;

        public BlogListViewHolder(View itemView) {
            super(itemView);
            textViewHeader  = itemView.findViewById(R.id.headerText);
            textViewDate    = itemView.findViewById(R.id.publishAt);
            imageView       = itemView.findViewById(R.id.image_view);
            overFlow        = itemView.findViewById(R.id.overflow);
            textViewDesc    = itemView.findViewById(R.id.fullDesc);
            buttonLike      = itemView.findViewById(R.id.favouriteVid);
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
            buttonLike.setTypeface(myCustomTypefaceBlack);
        }
    }
}
