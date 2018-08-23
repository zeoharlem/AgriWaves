package com.example.theophilus.agriwaves.Activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.theophilus.agriwaves.Adapters.SingleEpisodeRecyclerAdapter;
import com.example.theophilus.agriwaves.Models.Episodes;
import com.example.theophilus.agriwaves.Network.MyVolleySingleton;
import com.example.theophilus.agriwaves.R;
import com.example.theophilus.agriwaves.Utils.Helpers;
import com.example.theophilus.agriwaves.Utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeriesEpisodeActivity extends AppCompatActivity {
    private ImageLoader imageLoader;
    private MyVolleySingleton volleySingleton;
    private Typeface myCustomTypeface, myCustomTypefaceBold, myCustomTypefaceBlack;
    private TextView viewSeriesText;
    private String getCatSeries;

    private RecyclerView recyclerView;
    private ArrayList<Episodes> episodesArrayList;
    private SingleEpisodeRecyclerAdapter singleEpisodeRecyclerAdapter;
    private static final String POST_LIST = "episodes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_episode);

        Toolbar toolbar = findViewById(R.id.common_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewSeriesText              = findViewById(R.id.textSeriesHeader);
        final ImageView imageView   = findViewById(R.id.boxImageRow);
        viewSeriesText.setText(getIntent().getStringExtra("series_title"));
        getCatSeries                = getIntent().getStringExtra("series_cat_id");

        volleySingleton             = MyVolleySingleton.getInstance(this);
        imageLoader                 = volleySingleton.getImageLoader();

        String imageUrlRow          = getIntent().getStringExtra("series_video_url");
        this.imageLoader.get(imageUrlRow, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                imageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        setTypeFaceTask();

        recyclerView    = findViewById(R.id.seriesFrameRecycler);
        getTaskJsonRow(new VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Episodes> episodesArrayList) {
                singleEpisodeRecyclerAdapter    = new SingleEpisodeRecyclerAdapter(getApplicationContext(), episodesArrayList);
                recyclerView.setAdapter(singleEpisodeRecyclerAdapter);
            }
        });
        LinearLayoutManager layoutManager   = new LinearLayoutManager(this);
        DividerItemDecoration decoration    = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
    }

    private void getTaskJsonRow(final VolleyCallback volleyCallback){
        String urlStringRow         = Helpers.URL_STRING + "/series/episodes?cat_id="+getCatSeries;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlStringRow, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject   = new JSONObject(response);
                    if(jsonObject.getString("status").equals("OK")){
                        JSONObject dataFlow     = jsonObject.getJSONObject("response");
                        ArrayList<Episodes> pLi   = parseJson(dataFlow);
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
        MyVolleySingleton.getInstance(this).addToRequestQueue(stringRequest,"postList");
    }

    private ArrayList<Episodes> parseJson(JSONObject response){
        ArrayList<Episodes> postRow = new ArrayList<>();
        if(response != null && response.length() > 0){
            try {
                JSONArray array = response.getJSONArray("data");
                for(int i = 0; i < array.length(); i++){
                    Episodes post = new Episodes();
                    JSONObject current  = array.getJSONObject(i);
                    if(current.has("series_id")){
                        post.setSeriesId(current.getString("series_id"));
                    }
                    if(current.has("series_title") && !current.getString("series_title").isEmpty()){
                        post.setSeriesTitle(current.getString("series_title"));
                    }
                    if(current.has("series_desc")){
                        post.setSeriesDesc(current.getString("series_desc"));
                    }
                    if(current.has("image_urls") && !current.getString("image_urls").isEmpty()){
                        post.setSeriesImgs(current.getString("image_urls"));
                    }
                    if(current.has("date_created")){
                        post.setDateCreated(current.getString("date_created"));
                    }
                    if(current.has("video_id")){
                        post.setVideoId(current.getString("video_id"));
                    }
                    if(current.has("e_tag")){
                        post.seteTag(current.getString("e_tag"));
                    }
                    if(current.has("series_cat")){
                        post.setSeriesCatId(current.getString("series_cat"));
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

    private void setTypeFaceTask(){
        myCustomTypeface        = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        myCustomTypefaceBold    = Typeface.createFromAsset(getAssets(), "fonts/DaxlinePro-Bold.ttf");
        myCustomTypefaceBlack   = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-ExtraBold.ttf");
        viewSeriesText.setTypeface(myCustomTypefaceBold);
    }

    private interface VolleyCallback {
        public void onSuccess(ArrayList<Episodes> episodesArrayList);
    }
}
