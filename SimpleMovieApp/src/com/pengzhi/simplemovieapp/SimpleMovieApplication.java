package com.pengzhi.simplemovieapp;

import android.app.Application;
import android.content.Context;

public class SimpleMovieApplication extends Application {

	private static Context mAppContext;
	
    @Override
    public void onCreate() {
        
    	super.onCreate();
        this.setAppContext( getApplicationContext() );
    }
    
    public static Context getAppContext() {
    	
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
    	
        SimpleMovieApplication.mAppContext = mAppContext;
    }    
}
