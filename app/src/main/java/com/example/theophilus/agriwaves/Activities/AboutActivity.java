package com.example.theophilus.agriwaves.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.theophilus.agriwaves.R;
import com.example.theophilus.agriwaves.Utils.JustifyTextView;

public class AboutActivity extends AppCompatActivity {
    private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.common_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myCustomTypeface        = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");

        JustifyTextView justifyTextView1    = findViewById(R.id.about_first);
        JustifyTextView justifyTextView2    = findViewById(R.id.about_second);
        JustifyTextView justifyTextView3    = findViewById(R.id.about_third);
        JustifyTextView justifyTextView4    = findViewById(R.id.about_fourth);

        justifyTextView1.setTypeface(myCustomTypeface);
        justifyTextView2.setTypeface(myCustomTypeface);
        justifyTextView3.setTypeface(myCustomTypeface);
        justifyTextView4.setTypeface(myCustomTypeface);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
