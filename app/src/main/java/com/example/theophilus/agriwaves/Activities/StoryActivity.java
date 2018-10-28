package com.example.theophilus.agriwaves.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.theophilus.agriwaves.HomeActivity;
import com.example.theophilus.agriwaves.Models.Blog;
import com.example.theophilus.agriwaves.Network.MyVolleySingleton;
import com.example.theophilus.agriwaves.R;
import com.example.theophilus.agriwaves.Utils.Helpers;
import com.example.theophilus.agriwaves.Utils.L;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.theophilus.agriwaves.Utils.Helpers.URL_STRING;

public class StoryActivity extends HomeActivity implements View.OnClickListener{

    private Blog blog;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private static String postIdRow = null;
    private TextView textView, headerText, dateCreated;
    private MyVolleySingleton volleySingleton;
    private ImageView imageView;
    private Button favorite, sharer, sendAsMail;
    private LinearLayout linearLayout;
    private Typeface typeface, myTypeFace, myFaceBlack;
    private ImageLoader imageLoader;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        toolbar = findViewById(R.id.common_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        postIdRow   = getIntent().getStringExtra("blogId");
        textView    = findViewById(R.id.blogDetails);
        headerText  = findViewById(R.id.headerText);
        dateCreated = findViewById(R.id.publishAt);

        favorite    = findViewById(R.id.favouriteVid);
        sharer      = findViewById(R.id.shareVid);
        sendAsMail  = findViewById(R.id.sendAsMail);

        imageView   = findViewById(R.id.image_view);
        linearLayout    = findViewById(R.id.baseLayout);

        volleySingleton     = MyVolleySingleton.getInstance(this);
        imageLoader         = volleySingleton.getImageLoader();

        typeface    = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");
        myFaceBlack = Typeface.createFromAsset(getAssets(),"fonts/DaxlinePro-Bold.ttf");

        textView.setTypeface(typeface);
        headerText.setTypeface(myFaceBlack);

        favorite.setTypeface(myFaceBlack);
        sharer.setTypeface(myFaceBlack);
        sendAsMail.setTypeface(myFaceBlack);

        //Set OnClickListener
        favorite.setOnClickListener(this);
        sharer.setOnClickListener(this);
        sendAsMail.setOnClickListener(this);

        this.getBlogPostRow(postIdRow, new VolleyCallBack() {
            @Override
            public void onSuccess(Blog blogIdRowDetails) {
                blog    = blogIdRowDetails;
                headerText.setText(blogIdRowDetails.getPostTitle());
                dateCreated.setText(blogIdRowDetails.getPostDate());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    textView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                }
                else{
                    textView.setText(Html.fromHtml("<div style=\"text-align:justify; text-justify: inter-word; \">"
                            +blogIdRowDetails.getPostContent()+"</div>"));
                }

                linearLayout.setVisibility(View.VISIBLE);

                imageLoader.get(blogIdRowDetails.getFullImage(), new ImageLoader.ImageListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        imageView.setImageBitmap(response.getBitmap());
                    }
                });
                dateCreated.setText(blogIdRowDetails.getPostDate());
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

    private void getBlogPostRow(String postIdRow, final VolleyCallBack volleyCallBack){
        String URL_ROW_STRING   = URL_STRING+"/blog/view?id=" + postIdRow;
        StringRequest request   = new StringRequest(Request.Method.GET, URL_ROW_STRING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object   = new JSONObject(response);
                    if(object.getString("status").equals("OK")){
                        JSONObject getRespRow   = new JSONObject(object.getString("response"));
                        volleyCallBack.onSuccess(setBlogProperty(getRespRow));
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
        MyVolleySingleton.getInstance(this).addToRequestQueue(request, "BlogDetails");
    }

    private Blog setBlogProperty(JSONObject response){
        Blog blogPropertyRow    = new Blog();
        if(response != null && response.length() > 0){
            try {
                if(response.has("post_id") && !response.getString("post_id").isEmpty()){
                    blogPropertyRow.setPostId(response.getString("post_id"));
                    blogPropertyRow.setPostTitle(response.getString("post_title"));
                    blogPropertyRow.setFullImage(response.getString("full_image"));
                    blogPropertyRow.setPostContent(response.getString("post_content"));
                    blogPropertyRow.setCategory(response.getString("category"));
                    blogPropertyRow.setPostDate(response.getString("post_date"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                L.l(getApplicationContext(), e.getMessage());
            }
        }
        return blogPropertyRow;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.favouriteVid:
                shareIntentRow(blog);
                break;
            case R.id.shareVid:
                shareIntentRow(blog);
                break;
            case R.id.sendAsMail:
                shareViaMail(blog);
                break;
        }
    }

    private void shareIntentRow(Blog post){
        Intent intent   = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Intent.EXTRA_SUBJECT, post.getPostTitle());
        intent.putExtra(Intent.EXTRA_TEXT, "Just Read this "+post.getPostTitle()
                + Helpers.URL_STRING + "/blog/viewpage?blog="+post.getPostId());
        startActivity(Intent.createChooser(intent, "Shared from AgriwavesTv Mobile App"));
    }

    private void shareViaMail(Blog post){
        Intent intent   = new Intent(Intent.ACTION_SEND);

        intent.setType("message/rfc822");
        intent.setData(Uri.parse("mailto:"));
        String[] to = {"theophilus.alamu8@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Message from AgriwavesTv User");
        intent.putExtra(Intent.EXTRA_TEXT, "Just watched this "+post.getPostTitle()
                + Helpers.URL_STRING + "/blog/viewpage?blog="+post.getPostId());
        startActivity(Intent.createChooser(intent, "Blog: "+post.getPostTitle()));
    }

    private interface VolleyCallBack{
        void onSuccess(Blog blogIdRowDetails);
    }
}
