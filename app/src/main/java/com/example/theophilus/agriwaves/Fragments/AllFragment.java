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
import com.example.theophilus.agriwaves.Adapters.RecyclerViewAdapters;
import com.example.theophilus.agriwaves.Models.Post;
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
public class AllFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    RecyclerViewAdapters recyclerViewAdapters;
    ArrayList<Post> postArrayList;
    private static final String POST_LIST = "allPostRow";
    Boolean isScrolling                     = false;
    int currentItems, totalItems, scrollOutItems;
    int stOffsetRow                         = 0;
    ProgressBar progressBar;

    public AllFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view            = inflater.inflate(R.layout.fragment_all, container, false);
        recyclerView    = view.findViewById(R.id.recyclerViewAllFrag);

        //Set Progressive Bar Loader
        progressBar     = view.findViewById(R.id.progressBarRow);

        if(savedInstanceState != null){
            postArrayList           = savedInstanceState.getParcelableArrayList(POST_LIST);
            recyclerViewAdapters    = new RecyclerViewAdapters(getContext(), postArrayList);
            recyclerView.setAdapter(recyclerViewAdapters);
        }
        else{
            getTaskJsonRow(new VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Post> postArrayList) {
                    recyclerViewAdapters    = new RecyclerViewAdapters(getContext(), postArrayList);
                    recyclerViewAdapters.setPostArrayList(postArrayList);
                    recyclerView.setAdapter(recyclerViewAdapters);
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
        //setTextViewFontStyle();
        return view;
    }

    private void fetchNewTaskJsonRow() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stOffsetRow = stOffsetRow + 10;
                getTaskJsonRow(new VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Post> postArrayList) {
                        progressBar.setVisibility(View.GONE);
                        recyclerViewAdapters.addArrayListPostRow(postArrayList);
                    }
                }, stOffsetRow);
            }
        }, 5000);
    }

    private void getTaskJsonRow(final VolleyCallback volleyCallback, int offset){
        String urlStringRow         = Helpers.URL_STRING + "/list?offset="+offset;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlStringRow, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject   = new JSONObject(response);
                    if(jsonObject.getString("status").equals("OK")){
                        JSONObject dataFlow = jsonObject.getJSONObject("response");
                        ArrayList<Post> pLi = parseJson(dataFlow);
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
        MyVolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest,"postList");
    }

    private ArrayList<Post> parseJson(JSONObject response){
        ArrayList<Post> postRow = new ArrayList<>();
        if(response != null && response.length() > 0){
            try {
                JSONArray array = response.getJSONArray("items");
                for(int i = 0; i < array.length(); i++){
                    Post post     = new Post();
                    JSONObject current  = array.getJSONObject(i);
                    if(current.has("post_id")){
                        post.setPostId(current.getString("post_id"));
                    }
                    if(current.has("post_title") && !current.getString("post_title").isEmpty()){
                        post.setPostTitle(current.getString("post_title"));
                    }
                    if(current.has("date_reformat")){
                        post.setDateCreated(current.getString("date_reformat"));
                    }
                    if(current.has("post_desc") && !current.getString("post_desc").isEmpty()){
                        post.setPostDesc(current.getString("post_desc"));
                    }
                    if(current.has("category")){
                        post.setCategory(current.getString("category"));
                    }
                    if(current.has("image_url")){
                        post.setImageUrl(current.getString("image_url"));
                    }
                    if(current.has("video_id")){
                        post.setVideoId(current.getString("video_id"));
                    }
                    if(current.has("e_tag")){
                        post.seteTag(current.getString("e_tag"));
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
        if(recyclerViewAdapters != null) {
            outState.putParcelableArrayList(POST_LIST, recyclerViewAdapters.getPostArrayList());
        }
    }

    private interface VolleyCallback {
        public void onSuccess(ArrayList<Post> postArrayList);
    }
}
