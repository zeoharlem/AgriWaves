package com.example.theophilus.agriwaves.Network;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Theophilus on 6/23/2017.
 */

public class MyVolleySingleton {
    private static MyVolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context context;

    private MyVolleySingleton(Context context) {
        this.context = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized MyVolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyVolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(this.context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> tRequest, String tag) {
        tRequest.setTag(tag);
        getRequestQueue().add(tRequest);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void cancelRequest(Object tag){
        if(!mRequestQueue.equals(null)){
            mRequestQueue.cancelAll(tag);
        }
    }
}
