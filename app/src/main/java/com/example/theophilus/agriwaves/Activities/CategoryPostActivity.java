package com.example.theophilus.agriwaves.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.theophilus.agriwaves.Adapters.RecyclerViewAdapters;
import com.example.theophilus.agriwaves.Models.Post;
import com.example.theophilus.agriwaves.Network.MyVolleySingleton;
import com.example.theophilus.agriwaves.R;
import com.example.theophilus.agriwaves.Utils.Helpers;
import com.example.theophilus.agriwaves.Utils.L;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryPostActivity extends AppCompatActivity {

    private String id, table;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<Post> postArrayList;
    private RecyclerViewAdapters recyclerViewAdapters;
    private static final String CATPOST_LIST = "catPostList";
    private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;
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
        setContentView(R.layout.activity_category_post);
        id              = getIntent().getStringExtra("postId");
        myCustomTypefaceBlack   = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-ExtraBold.ttf");
        recyclerView    = findViewById(R.id.categoryPostRecyclerView);
        toolbar         = findViewById(R.id.common_toolbar);

        //Set Progressive Bar Loader
        progressBar     = findViewById(R.id.progressBarRow);

        //Set Text Typeface Here
        TextView viewTi = findViewById(R.id.titleHeader);
        viewTi.setText(getIntent().getStringExtra("postTitle"));
        viewTi.setTypeface(myCustomTypefaceBlack);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agriwaves Tv");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(savedInstanceState != null){
            postArrayList           = savedInstanceState.getParcelableArrayList(CATPOST_LIST);
            recyclerViewAdapters    = new RecyclerViewAdapters(this, postArrayList);
            recyclerView.setAdapter(recyclerViewAdapters);
        }
        else{
            getJsonTaskRow(new VolleyCallBack() {
                @Override
                public void onSuccess(ArrayList<Post> postArrayList) {
                    recyclerViewAdapters    = new RecyclerViewAdapters(getApplicationContext(), postArrayList);
                    recyclerView.setAdapter(recyclerViewAdapters);
                }
            }, 0);
        }

        final LinearLayoutManager layoutManager   = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
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
                getJsonTaskRow(new VolleyCallBack() {
                    @Override
                    public void onSuccess(ArrayList<Post> postArrayList) {
                        progressBar.setVisibility(View.GONE);
                        recyclerViewAdapters.addArrayListPostRow(postArrayList);
                    }
                }, stOffsetRow);
            }
        }, 5000);
    }

    private void getJsonTaskRow(final VolleyCallBack volleyCallBack, int offset){
        String URL_STRING           = Helpers.URL_STRING + "/category/getcatidpost?id="+id+"&dt=Blog&offset="+offset;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_STRING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject   = new JSONObject(response);
                    if (jsonObject.getString("status").equals("OK")){
                        JSONObject dataFlow = jsonObject.getJSONObject("response");
                        volleyCallBack.onSuccess(parseJson(dataFlow));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    L.l(getApplicationContext(), new Gson().toJson(e.getMessage()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.l(getApplicationContext(), new Gson().toJson(error));
            }
        });
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest, "catPostList");
    }

    private ArrayList<Post> parseJson(JSONObject response){
        ArrayList<Post> postRow = new ArrayList<>();
        if(response != null && response.length() > 0){
            try {
                JSONArray array = response.getJSONArray("data");
                for(int i = 0; i < array.length(); i++){
                    Post post     = new Post();
                    JSONObject current  = array.getJSONObject(i);
                    if(current.has("post_id") && !current.getString("post_id").isEmpty()){
                        post.setPostId(current.getString("post_id"));
                        post.setPostTitle(current.getString("post_title"));
                        post.setDateCreated(current.getString("date_reformat"));
                        post.setPostDesc(current.getString("post_desc"));
                        post.setCategory(current.getString("category"));
                        post.setImageUrl(current.getString("image_url"));
                        post.setVideoId(current.getString("video_id"));
                        //post.setImageUrl(current.getString("mid_image"));
                    }
                    postRow.add(post);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                L.l(this, "JSON Exception: "+e.getMessage());
            }
        }
        return postRow;
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(recyclerViewAdapters != null) {
            outState.putParcelableArrayList(CATPOST_LIST, recyclerViewAdapters.getPostArrayList());
        }
    }

    interface VolleyCallBack{
        void onSuccess(ArrayList<Post> postArrayList);
    }
}
