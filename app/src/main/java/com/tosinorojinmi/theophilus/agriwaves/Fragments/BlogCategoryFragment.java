package com.tosinorojinmi.theophilus.agriwaves.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.tosinorojinmi.theophilus.agriwaves.Adapters.BlogCategoryRecyclerAdapter;
import com.tosinorojinmi.theophilus.agriwaves.Models.Category;
import com.tosinorojinmi.theophilus.agriwaves.Network.MyVolleySingleton;
import com.tosinorojinmi.theophilus.agriwaves.R;
import com.tosinorojinmi.theophilus.agriwaves.Utils.Helpers;
import com.tosinorojinmi.theophilus.agriwaves.Utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogCategoryFragment extends Fragment {
    RecyclerView recyclerView;
    BlogCategoryRecyclerAdapter categoryRecyclerAdapter;
    ArrayList<Category> categoryArrayList;
    private static final String CAT_LIST    = "cateList";

    public BlogCategoryFragment() {
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
        View view       = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView    = view.findViewById(R.id.categoryRecyclerView);

        if(savedInstanceState != null){
            categoryArrayList   = savedInstanceState.getParcelableArrayList(CAT_LIST);
            categoryRecyclerAdapter = new BlogCategoryRecyclerAdapter(categoryArrayList, getContext());
            recyclerView.setAdapter(categoryRecyclerAdapter);
        }
        else {
            getJsonCategoryTask(new VolleyCallBack() {
                @Override
                public void onSuccess(ArrayList<Category> category) {
                    categoryArrayList = category;
                    categoryRecyclerAdapter = new BlogCategoryRecyclerAdapter(category, getContext());
                    recyclerView.setAdapter(categoryRecyclerAdapter);
                }
            });
        }
        LinearLayoutManager layoutManager   = new LinearLayoutManager(getContext());
        DividerItemDecoration decoration    = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        return view;
    }

    private void getJsonCategoryTask(final VolleyCallBack volleyCallBack){
        String URL_STRING           = Helpers.URL_STRING + "/blog/blogcat";
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
                    L.l(getContext(), new Gson().toJson(e.getMessage()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.l(getContext(), new Gson().toJson(error));
            }
        });
        MyVolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest, "categoryList");
    }

    private ArrayList<Category> parseJson(JSONObject response){
        ArrayList<Category> postRow = new ArrayList<>();
        if(response != null && response.length() > 0){
            try {
                JSONArray array = response.getJSONArray("data");
                for(int i = 0; i < array.length(); i++){
                    Category category   = new Category();
                    JSONObject current  = array.getJSONObject(i);
                    if(current.has("category_id") && !current.getString("category_id").isEmpty()){
                        category.setCategoryId(current.getString("category_id"));
                        category.setCategoryDesc(current.getString("category_desc"));
                        category.setCategoryName(current.getString("category_name"));
                        category.setCategoryFirstLetter(current.getString("first_letter"));
                        category.setCategoryBodyLetter(current.getString("body_letter"));
                    }
                    postRow.add(category);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return postRow;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(categoryRecyclerAdapter != null){
            outState.putParcelableArrayList(CAT_LIST, categoryRecyclerAdapter.getCategoryArrayList());
        }
    }

    private interface VolleyCallBack{
        public void onSuccess(ArrayList<Category> category);
    }

}
