package com.example.theophilus.agriwaves.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.theophilus.agriwaves.Adapters.SeriesRecyclerAdapter;
import com.example.theophilus.agriwaves.Models.Series;
import com.example.theophilus.agriwaves.Network.MyVolleySingleton;
import com.example.theophilus.agriwaves.R;
import com.example.theophilus.agriwaves.Utils.Helpers;
import com.example.theophilus.agriwaves.Utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeriesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SeriesRecyclerAdapter seriesRecyclerAdapter;
    ArrayList<Series> seriesArrayList;
    private static final String POST_LIST = "seriesRow";

    Boolean isScrolling                     = false;
    int currentItems, totalItems, scrollOutItems;
    int stOffsetRow                         = 0;
    ProgressBar progressBar;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        Toolbar toolbar = findViewById(R.id.common_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView    = findViewById(R.id.seriesRecyclerView);
        progressBar     = findViewById(R.id.progressBarRow);

        getTaskJsonRow(new VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Series> seriesArrayList) {
                //L.l(getContext(), new Gson().toJson(seriesArrayList));
                seriesRecyclerAdapter   = new SeriesRecyclerAdapter(getApplicationContext(), seriesArrayList);
                recyclerView.setAdapter(seriesRecyclerAdapter);
            }
        }, 0);

        final LinearLayoutManager layoutManager   = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration decoration    = new DividerItemDecoration(getApplicationContext(), layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems    = layoutManager.getChildCount();
                totalItems      = layoutManager.getItemCount();
                scrollOutItems  = layoutManager.findFirstVisibleItemPosition();
                if(isScrolling && (currentItems + scrollOutItems == totalItems)){
                    isScrolling = false;
                    fetchNewTaskJsonRow();
                }
            }
        });
    }

    private void fetchNewTaskJsonRow() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stOffsetRow = stOffsetRow + 3;
                getTaskJsonRow(new VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Series> seriesArrayList) {
                        progressBar.setVisibility(View.GONE);
                        seriesRecyclerAdapter.addArrayBlogListRow(seriesArrayList);
                    }
                }, stOffsetRow);
            }
        }, 5000);
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

    private void getTaskJsonRow(final VolleyCallback volleyCallback, int offset){
        String urlStringRow         = Helpers.URL_STRING + "/series?offset="+offset;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlStringRow, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject   = new JSONObject(response);
                    if(jsonObject.getString("status").equals("OK")){
                        JSONObject dataFlow     = jsonObject.getJSONObject("response");
                        ArrayList<Series> pLi   = parseJson(dataFlow);
                        volleyCallback.onSuccess(pLi);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    L.l(getApplicationContext(), "Server Error: "+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.l(getApplicationContext(), "Error: "+error);
            }
        });
        MyVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,"postList");
    }

    private ArrayList<Series> parseJson(JSONObject response){
        ArrayList<Series> postRow = new ArrayList<>();
        if(response != null && response.length() > 0){
            try {
                JSONArray array = response.getJSONArray("data");
                for(int i = 0; i < array.length(); i++){
                    Series post = new Series();
                    JSONObject current  = array.getJSONObject(i);
                    if(current.has("seriescat_id")){
                        post.setSeriesId(current.getString("seriescat_id"));
                    }
                    if(current.has("series_title") && !current.getString("series_title").isEmpty()){
                        post.setSeriesTitle(current.getString("series_title"));
                    }
                    if(current.has("series_desc")){
                        post.setSeriesDesc(current.getString("series_desc"));
                    }
                    if(current.has("series_imgs") && !current.getString("series_imgs").isEmpty()){
                        post.setSeriesImgs(current.getString("series_imgs"));
                    }
                    if(current.has("date_created")){
                        post.setDateCreated(current.getString("date_created"));
                    }
                    if(current.has("series_count")){
                        post.setTotalNumber(current.getString("series_count"));
                    }
                    postRow.add(post);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                L.l(getApplicationContext(), "JSON Exception: "+e.getMessage());
            }
        }
        return postRow;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(seriesRecyclerAdapter != null){

        }
    }

    private interface VolleyCallback {
        public void onSuccess(ArrayList<Series> seriesArrayList);
    }

}
