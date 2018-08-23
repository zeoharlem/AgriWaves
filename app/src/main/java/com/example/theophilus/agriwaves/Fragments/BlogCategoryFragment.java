package com.example.theophilus.agriwaves.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.theophilus.agriwaves.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogCategoryFragment extends Fragment {


    public BlogCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog_category, container, false);
    }

}
