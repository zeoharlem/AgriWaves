package com.example.theophilus.agriwaves.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.theophilus.agriwaves.Adapters.RecyclerViewAdapters;
import com.example.theophilus.agriwaves.Models.Blog;
import com.example.theophilus.agriwaves.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllBlogFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    RecyclerViewAdapters recyclerViewAdapters;
    ArrayList<Blog> blogArrayList;
    private static final String BLOG_LIST = "blogRow";

    public AllBlogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view            = inflater.inflate(R.layout.fragment_all_blog, container, false);
        recyclerView    = view.findViewById(R.id.recyclerViewAllFrag);

        return view;
    }

}
