package com.tosinorojinmi.theophilus.agriwaves;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tosinorojinmi.theophilus.agriwaves.Activities.AboutActivity;
import com.tosinorojinmi.theophilus.agriwaves.Activities.ContactActivity;
import com.tosinorojinmi.theophilus.agriwaves.Activities.SeriesActivity;
import com.tosinorojinmi.theophilus.agriwaves.Activities.TeamActivity;
import com.tosinorojinmi.theophilus.agriwaves.Activities.VideoPlayerActivity;
import com.tosinorojinmi.theophilus.agriwaves.Adapters.BlogPageAdapter;
import com.tosinorojinmi.theophilus.agriwaves.Utils.CustomTypefaceSpan;

public class BlogActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        toolbar = findViewById(R.id.common_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AgriwavesTv");

        drawerLayout    = findViewById(R.id.drawer_layout);
        navigationView  = findViewById(R.id.navigationView);

        tabLayout       = findViewById(R.id.tabLayoutRow);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        appBarLayout    = findViewById(R.id.appbar);

        if(drawerLayout != null) {
            toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    if(toggle != null) {
                        toggle.setDrawerIndicatorEnabled(true);
                        toggle.syncState();
                    }
                    isShow = false;
                }
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("Blog "));
        tabLayout.addTab(tabLayout.newTab().setText("Categories"));

        changeTabsFont();

        viewPager       = findViewById(R.id.viewPagerRow);
        BlogPageAdapter pageAdapters   = new BlogPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapters);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);

        Menu menu                     = navigationView.getMenu();

        for(int i = 0; i < menu.size(); i++){
            MenuItem menuItem   = menu.getItem(i);
            SubMenu subMenu     = menuItem.getSubMenu();
            if(subMenu != null && subMenu.size() > 0){
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(menuItem);
        }
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homepage:
                        item.setChecked(true);
                        Intent intent   = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        if(drawerLayout != null) {
                            drawerLayout.closeDrawers();
                        }
                        return true;
                    case R.id.about_us:
                        item.setChecked(true);
                        Intent aboutIntent  = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(aboutIntent);
                        if(drawerLayout != null) {
                            drawerLayout.closeDrawers();
                        }
                        return true;
                    case R.id.series_layout:
                        item.setChecked(true);
                        Intent series  = new Intent(getApplicationContext(), SeriesActivity.class);
                        startActivity(series);
                        if(drawerLayout != null) {
                            drawerLayout.closeDrawers();
                        }
                        return true;
                    case R.id.live_network:
                        item.setChecked(true);
                        Intent liveIntent   = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                        startActivity(liveIntent);
                        if(drawerLayout != null) {
                            drawerLayout.closeDrawers();
                        }
                        return true;
                    case R.id.contacts:
                        item.setChecked(true);
                        Intent contactIntent   = new Intent(getApplicationContext(), ContactActivity.class);
                        startActivity(contactIntent);
                        if(drawerLayout != null) {
                            drawerLayout.closeDrawers();
                        }
                        return true;
                    case R.id.blog:
                        item.setChecked(true);
                        Intent blogIntent   = new Intent(getApplicationContext(), BlogActivity.class);
                        startActivity(blogIntent);
                        if(drawerLayout != null) {
                            drawerLayout.closeDrawers();
                        }
                        return true;
                    case R.id.settings:
                        item.setChecked(true);
                        Intent teamIntent   = new Intent(getApplicationContext(), TeamActivity.class);
                        startActivity(teamIntent);
                        if(drawerLayout != null) {
                            drawerLayout.closeDrawers();
                        }
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-ExtraBold.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void changeTabsFont() {
        ViewGroup childTabLayout = (ViewGroup) tabLayout.getChildAt(0);
        for (int i = 0; i < childTabLayout.getChildCount(); i++) {
            ViewGroup viewTab = (ViewGroup) childTabLayout.getChildAt(i);
            for (int j = 0; j < viewTab.getChildCount(); j++) {
                View tabTextView = viewTab.getChildAt(j);
                if (tabTextView instanceof TextView) {
                    Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Bold.ttf");
                    ((TextView) tabTextView).setTypeface(typeface);
                    ((TextView) tabTextView).setTextSize(14);
                    //((TextView) tabTextView).setTextSize(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.text_small));
                }
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
