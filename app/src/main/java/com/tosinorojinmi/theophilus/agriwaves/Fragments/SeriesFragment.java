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
import com.tosinorojinmi.theophilus.agriwaves.Adapters.SeriesRecyclerAdapter;
import com.tosinorojinmi.theophilus.agriwaves.Models.Series;
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
public class SeriesFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    SeriesRecyclerAdapter seriesRecyclerAdapter;
    ArrayList<Series> seriesArrayList;
    private static final String POST_LIST = "seriesRow";

    public SeriesFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view            = inflater.inflate(R.layout.fragment_series, container, false);
        recyclerView    = view.findViewById(R.id.seriesRecyclerView);

        getTaskJsonRow(new VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Series> seriesArrayList) {
                //L.l(getContext(), new Gson().toJson(seriesArrayList));
                seriesRecyclerAdapter   = new SeriesRecyclerAdapter(getContext(), seriesArrayList);
                recyclerView.setAdapter(seriesRecyclerAdapter);
            }
        });

        LinearLayoutManager layoutManager   = new LinearLayoutManager(getContext());
        DividerItemDecoration decoration    = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        return view;
    }

    private void getTaskJsonRow(final VolleyCallback volleyCallback){
        String urlStringRow         = Helpers.URL_STRING + "/series/";
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
                L.l(getContext(), "JSON Exception: "+e.getMessage());
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
