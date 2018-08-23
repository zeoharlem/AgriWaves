package com.example.theophilus.agriwaves.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.theophilus.agriwaves.Fragments.AllFragment;
import com.example.theophilus.agriwaves.Fragments.CategoryFragment;
import com.example.theophilus.agriwaves.Fragments.SeriesFragment;

/**
 * Created by Theophilus on 8/13/2018.
 */

public class PageAdapters extends FragmentStatePagerAdapter {

    public PageAdapters(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                AllFragment fragment    = new AllFragment();
                return fragment;
            case 1:
                SeriesFragment seriesFragment   = new SeriesFragment();
                return seriesFragment;
            case 2:
            CategoryFragment categoryFragment    = new CategoryFragment();
                return categoryFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
