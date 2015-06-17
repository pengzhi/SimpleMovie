package com.pengzhi.simplemovieapp;

import com.pengzhi.simplemovieapp.common.Movie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

public class MainActivity extends FragmentActivity implements FragmentMovieList.MovieDetailsPasser {

    private static final String TAG = "MainActivity";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        showMovieDetailsIfAvailable();
        
    }

	private void showMovieDetailsIfAvailable(){
		
		Fragment fragment = getSupportFragmentManager().findFragmentById( R.id.movie_details );
		if ( fragment != null ){
			
			findViewById( R.id.movie_details ).setVisibility( View.VISIBLE );
		}
	}
	
	@Override
	public void show(Movie movie) {
		
		Log.d(TAG, "show movie: " + movie);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		FragmentMovieDetails fragment = new FragmentMovieDetails();
		Bundle bundle = new Bundle();
		
		bundle.putParcelable( Movie.KEY, movie );
		fragment.setArguments( bundle );
		fragmentTransaction.replace( R.id.movie_details, fragment );
		fragmentTransaction.commit();
		
		findViewById( R.id.movie_details ).setVisibility( View.VISIBLE );
	}
	
}
