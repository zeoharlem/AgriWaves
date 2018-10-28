package com.example.theophilus.agriwaves.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.theophilus.agriwaves.Activities.BlogListActivity;
import com.example.theophilus.agriwaves.Models.Category;
import com.example.theophilus.agriwaves.Network.MyVolleySingleton;
import com.example.theophilus.agriwaves.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Theophilus on 9/15/2018.
 */

public class BlogCategoryRecyclerAdapter extends RecyclerView.Adapter<BlogCategoryRecyclerAdapter.MyBlogCategoryViewHolder>{

    private LayoutInflater layoutInflater;
    private MyVolleySingleton volleySingleton;
    private ArrayList<Category> categoryArrayList;
    private Context context;

    public BlogCategoryRecyclerAdapter(ArrayList<Category> categoryArrayList, Context context) {
        this.context            = context;
        this.categoryArrayList  = categoryArrayList;
        layoutInflater          = LayoutInflater.from(context);
        volleySingleton         = MyVolleySingleton.getInstance(context);
    }

    public ArrayList<Category> getCategoryArrayList() {
        return categoryArrayList;
    }

    public void setCategoryArrayList(ArrayList<Category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyBlogCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = layoutInflater.inflate(R.layout.category_item_row, parent, false);
        return new MyBlogCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBlogCategoryViewHolder holder, int position) {
        final Category category   = categoryArrayList.get(position);
        holder.circularTextView.setBackgroundColor(getRandomColor());
        holder.circularTextView.setText(category.getCategoryFirstLetter());
        holder.categoryText.setText(category.getCategoryName());

        holder.boxLayCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //L.l(context, category.getCategoryName());
                Intent intent   = new Intent(context, BlogListActivity.class);
                intent.putExtra("catId", category.getCategoryId());
                intent.putExtra("catTitle", category.getCategoryName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    private int getRandomColor(){
        Random random   = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public class MyBlogCategoryViewHolder extends RecyclerView.ViewHolder{

        public TextView categoryText, circularTextView;
        private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;
        public RelativeLayout boxLayCategory;

        public MyBlogCategoryViewHolder(View itemView) {
            super(itemView);
            categoryText        = itemView.findViewById(R.id.textCategory);
            circularTextView    = itemView.findViewById(R.id.circularTextView);
            boxLayCategory      = itemView.findViewById(R.id.boxLayCategory);
            setTypeFaceTask(itemView);
        }

        private void setTypeFaceTask(View itemView){
            myCustomTypeface        = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/OpenSans-Regular.ttf");
            myCustomTypefaceBold    = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/DaxlinePro-Bold.ttf");
            myCustomTypefaceBlack   = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/OpenSans-ExtraBold.ttf");
            categoryText.setTypeface(myCustomTypefaceBold);
            circularTextView.setTypeface(myCustomTypefaceBlack);
        }
    }
}
