package com.example.theophilus.agriwaves.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.theophilus.agriwaves.Models.Post;
import com.example.theophilus.agriwaves.Network.MyVolleySingleton;

import java.util.ArrayList;

/**
 * Created by Theophilus on 8/31/2018.
 */

public class CategoryPostRecyclerAdapter extends RecyclerView.Adapter<CategoryPostRecyclerAdapter.MyCategoryPostViewHolder>{


    private LayoutInflater layoutInflater;
    private MyVolleySingleton volleySingleton;
    private ArrayList<Post> postArrayList;
    private Context context;
    public CategoryPostRecyclerAdapter(){

    }

    @NonNull
    @Override
    public MyCategoryPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCategoryPostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyCategoryPostViewHolder extends RecyclerView.ViewHolder{

        public MyCategoryPostViewHolder(View itemView) {
            super(itemView);
        }
    }
}
