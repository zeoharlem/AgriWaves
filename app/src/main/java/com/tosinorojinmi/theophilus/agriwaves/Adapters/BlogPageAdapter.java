package com.tosinorojinmi.theophilus.agriwaves.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tosinorojinmi.theophilus.agriwaves.Fragments.AllBlogFragment;
import com.tosinorojinmi.theophilus.agriwaves.Fragments.BlogCategoryFragment;

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
