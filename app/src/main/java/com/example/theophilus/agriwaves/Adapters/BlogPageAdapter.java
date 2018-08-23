package com.example.theophilus.agriwaves.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.theophilus.agriwaves.Fragments.AllBlogFragment;
import com.example.theophilus.agriwaves.Fragments.AllFragment;
import com.example.theophilus.agriwaves.Fragments.BlogCategoryFragment;
import com.example.theophilus.agriwaves.Fragments.CategoryFragment;
import com.example.theophilus.agriwaves.Fragments.SeriesFragment;

/**
 * Created by Theophilus on 8/18/2018.
 */

public class BlogPageAdapter extends FragmentStatePagerAdapter {

    public BlogPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                AllBlogFragment allBlogFragment    = new AllBlogFragment();
                return allBlogFragment;
            case 1:
                BlogCategoryFragment blogCategoryFragment   = new BlogCategoryFragment();
                return blogCategoryFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
