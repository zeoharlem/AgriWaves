package com.example.theophilus.agriwaves.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.theophilus.agriwaves.Adapters.BlogRecyclerAdapter;
import com.example.theophilus.agriwaves.Models.Blog;
import com.example.theophilus.agriwaves.Network.MyVolleySingleton;
import com.example.theophilus.agriwaves.R;
import com.example.theophilus.agriwaves.Utils.Helpers;
import com.example.theophilus.agriwaves.Utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllBlogFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<Blog> blogArrayList;
    BlogRecyclerAdapter blogRecyclerAdapter;
    private static final String BLOG_LIST   = "blogRow";
    Boolean isScrolling                     = false;
    int currentItems, totalItems, scrollOutItems;
    int stOffsetRow                  = 0;
    ProgressBar progressBar;

    public AllBlogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view            = inflater.inflate(R.layout.fragment_all_blog, container, false);
        recyclerView    = view.findViewById(R.id.blogRecyclerView);
        progressBar     = view.findViewById(R.id.progressBarRow);

        if(savedInstanceState != null){
            progressBar.setVisibility(View.GONE);
            blogArrayList          = savedInstanceState.getParcelableArrayList(BLOG_LIST);
            blogRecyclerAdapter    = new BlogRecyclerAdapter(getContext(), blogArrayList);
            recyclerView.setAdapter(blogRecyclerAdapter);
        }
        else{
            getTaskJsonRow(new VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Blog> blogArrayList) {
                    progressBar.setVisibility(View.GONE);
                    blogRecyclerAdapter    = new BlogRecyclerAdapter(getContext(), blogArrayList);
                    blogRecyclerAdapter.setBlogArrayList(blogArrayList);
                    recyclerView.setAdapter(blogRecyclerAdapter);
                }
            }, 0);
        }

        final LinearLayoutManager layoutManager   = new LinearLayoutManager(getContext());
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
        return view;
    }

    private void fetchNewTaskJsonRow(){
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stOffsetRow = stOffsetRow + 10;
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
        String urlStringRow         = Helpers.URL_STRING + "/blog?offset="+offset;
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
                    L.l(getContext(), "Server Error: "+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.l(getContext(), "Error: "+error);
            }
        });
        MyVolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest,"blogPost");
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
                L.l(getContext(), "JSON Exception: "+e.getMessage());
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
