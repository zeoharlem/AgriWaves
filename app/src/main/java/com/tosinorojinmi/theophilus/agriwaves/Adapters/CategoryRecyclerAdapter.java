package com.tosinorojinmi.theophilus.agriwaves.Adapters;

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

import com.tosinorojinmi.theophilus.agriwaves.Activities.CategoryPostActivity;
import com.tosinorojinmi.theophilus.agriwaves.Models.Category;
import com.tosinorojinmi.theophilus.agriwaves.Network.MyVolleySingleton;
import com.tosinorojinmi.theophilus.agriwaves.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Theophilus on 8/30/2018.
 */

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyCategoryViewHolder>{

    private LayoutInflater layoutInflater;
    private MyVolleySingleton volleySingleton;
    private ArrayList<Category> categoryArrayList;
    private Context context;

    public CategoryRecyclerAdapter(ArrayList<Category> categoryArrayList, Context context) {
        this.context            = context;
        this.categoryArrayList  = categoryArrayList;
        layoutInflater          = LayoutInflater.from(context);
        volleySingleton         = MyVolleySingleton.getInstance(context);
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
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
    public MyCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = layoutInflater.inflate(R.layout.category_item_row, parent, false);
        return new MyCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCategoryViewHolder holder, int position) {
        final Category category   = categoryArrayList.get(position);
        holder.circularTextView.setBackgroundColor(getRandomColor());
        holder.circularTextView.setText(category.getCategoryFirstLetter());
        holder.categoryText.setText(category.getCategoryName());

        holder.boxLayCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent   = new Intent(context, CategoryPostActivity.class);
                intent.putExtra("postId", category.getCategoryId());
                intent.putExtra("postTitle", category.getCategoryName());
                context.startActivity(intent);
            }
        });
    }

    private int getRandomColor(){
        Random random   = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class MyCategoryViewHolder extends RecyclerView.ViewHolder{
        public TextView categoryText, circularTextView;
        private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;
        public RelativeLayout boxLayCategory;

        public MyCategoryViewHolder(View itemView) {
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
