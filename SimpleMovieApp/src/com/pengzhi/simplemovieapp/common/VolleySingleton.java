package com.pengzhi.simplemovieapp.common;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.pengzhi.simplemovieapp.SimpleMovieApplication;

public enum VolleySingleton {
   
    INSTANCE;
    
    private static final String TAG = "VolleySingleton"; 
    
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    
    private VolleySingleton(){
        
        mRequestQueue = Volley.newRequestQueue( SimpleMovieApplication.getAppContext() );
        mImageLoader = new ImageLoader(this.mRequestQueue, new BitmapLruCache() );
    }

    public static VolleySingleton getInstance(){
        return INSTANCE;
    }

    public RequestQueue getRequestQueue(){
        return INSTANCE.mRequestQueue;
    }
    
    public ImageLoader getImageLoader(){
        return INSTANCE.mImageLoader;
    }

}
