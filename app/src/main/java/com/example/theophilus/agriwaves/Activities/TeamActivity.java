package com.example.theophilus.agriwaves.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.theophilus.agriwaves.R;

public class TeamActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        toolbar = findViewById(R.id.common_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myCustomTypeface        = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        myCustomTypefaceBold    = Typeface.createFromAsset(getAssets(), "fonts/DaxlinePro-Bold.ttf");
        myCustomTypefaceBlack   = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-ExtraBold.ttf");

        TextView felixTitle     = findViewById(R.id.felix_name);
        TextView ericTitle      = findViewById(R.id.eric_name);
        TextView bilyaminu      = findViewById(R.id.bilyaminu_name);
        TextView martins        = findViewById(R.id.martins_name);
        TextView felixDesc      = findViewById(R.id.felix_desc);
        TextView ericDesc       = findViewById(R.id.eric_desc);
        TextView bilyaminuDesc  = findViewById(R.id.bilyaminu_desc);
        TextView martinsDesc    = findViewById(R.id.martins_desc);

        TextView others         = findViewById(R.id.others);

        felixTitle.setTypeface(myCustomTypefaceBold);
        ericTitle.setTypeface(myCustomTypefaceBold);
        bilyaminu.setTypeface(myCustomTypefaceBold);
        martins.setTypeface(myCustomTypefaceBold);

        felixDesc.setTypeface(myCustomTypeface);
        ericDesc.setTypeface(myCustomTypeface);
        bilyaminuDesc.setTypeface(myCustomTypeface);
        martinsDesc.setTypeface(myCustomTypeface);
        others.setTypeface(myCustomTypeface);
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
