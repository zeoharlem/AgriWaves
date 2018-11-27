package com.tosinorojinmi.theophilus.agriwaves.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.tosinorojinmi.theophilus.agriwaves.Adapters.BlogRecyclerAdapter;
import com.tosinorojinmi.theophilus.agriwaves.Models.Blog;
import com.tosinorojinmi.theophilus.agriwaves.Network.MyVolleySingleton;
import com.tosinorojinmi.theophilus.agriwaves.R;
import com.tosinorojinmi.theophilus.agriwaves.Utils.Helpers;
import com.tosinorojinmi.theophilus.agriwaves.Utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BlogListActivity extends AppCompatActivity {
    View view;
    private String id;
    RecyclerView recyclerView;
    ArrayList<Blog> blogArrayList;
    BlogRecyclerAdapter blogRecyclerAdapter;
    private static final String BLOG_LIST = "blogListRow";
    private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;
    private Toolbar toolbar;

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
        setContentView(R.layout.activity_blog_list);

        recyclerView    = findViewById(R.id.blogCategoryRecyclerView);
        id              = getIntent().getStringExtra("catId");
        toolbar         = findViewById(R.id.common_toolbar);
        //Set Progressive Bar Loader
        progressBar     = findViewById(R.id.progressBarRow);
        //Set Text Typeface Here
        TextView viewTi = findViewById(R.id.titleHeader);

        viewTi.setText(getIntent().getStringExtra("catTitle"));
        myCustomTypefaceBlack   = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-ExtraBold.ttf");
        viewTi.setTypeface(myCustomTypefaceBlack);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AgriWaves");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if(savedInstanceState != null){
            blogArrayList          = savedInstanceState.getParcelableArrayList(BLOG_LIST);
            blogRecyclerAdapter    = new BlogRecyclerAdapter(getApplicationContext(), blogArrayList);
            recyclerView.setAdapter(blogRecyclerAdapter);
        }
        else{
            getTaskJsonRow(new VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Blog> blogArrayList) {
                    blogRecyclerAdapter    = new BlogRecyclerAdapter(getApplicationContext(), blogArrayList);
                    blogRecyclerAdapter.setBlogArrayList(blogArrayList);
                    recyclerView.setAdapter(blogRecyclerAdapter);
                }
            }, 0);
        }
        final LinearLayoutManager layoutManager   = new LinearLayoutManager(getApplicationContext());
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

    private void fetchNewTaskJsonRow(){
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stOffsetRow = stOffsetRow + 3;
                getTaskJsonRow(new VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Blog> blogArrayList) {
                        progressBar.setVisibility(View.GONE);
                        blogRecyclerAdapter.addArrayBlogListRow(blogArrayList);
                    }
                }, stOffsetRow);
            }
        }, 5000);
    }

    private void getTaskJsonRow(final VolleyCallback volleyCallback, int offset){
        String urlStringRow         = Helpers.URL_STRING + "/blog/bloglist?cat_id="+id+"&offset="+offset;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlStringRow, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject   = new JSONObject(response);
                    if(jsonObject.getString("status").equals("OK")){
                        JSONObject dataFlow = jsonObject.getJSONObject("response");
                        ArrayList<Blog> pLi = parseJson(dataFlow);
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
        MyVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,"blogListRow");
    }

    private ArrayList<Blog> parseJson(JSONObject response){
        ArrayList<Blog> postRow = new ArrayList<>();
        if(response != null && response.length() > 0){
            try {
                JSONArray array = response.getJSONArray("items");
                for(int i = 0; i < array.length(); i++){
                    Blog post     = new Blog();
                    JSONObject current  = array.getJSONObject(i);
                    if(current.has("post_id")){
                        post.setPostId(current.getString("post_id"));
                    }
                    if(current.has("post_title") && !current.getString("post_title").isEmpty()){
                        post.setPostTitle(current.getString("post_title"));
                    }
                    if(current.has("post_date")){
                        post.setPostDate(current.getString("post_date"));
                    }
                    if(current.has("post_excerpt") && !current.getString("post_excerpt").isEmpty()){
                        post.setPostExcerpt(current.getString("post_excerpt"));
                    }
                    if(current.has("post_status")){
                        post.setPostStatus(current.getString("post_status"));
                    }
                    if(current.has("full_image")){
                        post.setFullImage(current.getString("full_image"));
                    }
                    if(current.has("mid_image")){
                        post.setMidImage(current.getString("mid_image"));
                    }
                    if(current.has("category")){
                        post.setCategory(current.getString("category"));
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
        if(blogRecyclerAdapter != null) {
            outState.putParcelableArrayList(BLOG_LIST, blogRecyclerAdapter.getBlogArrayList());
        }
    }

    private interface VolleyCallback {
        public void onSuccess(ArrayList<Blog> blogArrayList);
    }
}
