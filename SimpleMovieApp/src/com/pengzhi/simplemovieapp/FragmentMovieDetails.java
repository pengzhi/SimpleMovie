package com.pengzhi.simplemovieapp;

import com.android.volley.toolbox.ImageLoader;
import com.pengzhi.simplemovieapp.common.Movie;
import com.pengzhi.simplemovieapp.common.MovieDetails;
import com.pengzhi.simplemovieapp.common.NetworkImageView;
import com.pengzhi.simplemovieapp.common.VolleySingleton;
import com.pengzhi.simplemovieapp.task.BaseTask.OnPostExecuteListener;
import com.pengzhi.simplemovieapp.task.BaseTask.OnPreExecuteListener;
import com.pengzhi.simplemovieapp.task.GetMovieDetailsTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FragmentMovieDetails extends Fragment {

	private static final String TAG = "FragmentMovieDetails";

	private TextView title;
	private TextView genreRelease;
	private TextView plot;
	private NetworkImageView poster;
	private ImageLoader volleyImageLoader;
	private MovieDetails movieDetails;
	private ProgressBar progressBar;
	

	@Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	View view = inflater.inflate( R.layout.fragment_movie_details, container, false );
    	title = (TextView) view.findViewById( R.id.title );
    	genreRelease = (TextView) view.findViewById( R.id.genre_release );
    	plot = (TextView) view.findViewById( R.id.plot );
    	poster = (NetworkImageView) view.findViewById( R.id.poster );
    	progressBar = (ProgressBar) view.findViewById( R.id.progress_bar );
    	
    	volleyImageLoader = VolleySingleton.getInstance().getImageLoader();
    
    	
    	if (savedInstanceState != null) {
    		
            movieDetails = (MovieDetails) savedInstanceState.getParcelable( MovieDetails.KEY );
			doView( movieDetails );
			
        } else {
        	
            Movie movie = getArguments().getParcelable( Movie.KEY ); 
            GetMovieDetailsTask get = new GetMovieDetailsTask( movie.getImdbID() );
            get.setOnPreExecuteListener( onPreListener );
            get.setOnPostExecuteListener( onPostListener );
            get.execute();
        
        }
    	
    	return view;
	}
	
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	
        super.onSaveInstanceState(savedInstanceState);
        
        savedInstanceState.putParcelable( MovieDetails.KEY, movieDetails );
    }
    
    private void doView( MovieDetails movieDetails ){
    	
    	if ( movieDetails == null ){ return; }
    	
		title.setText( movieDetails.getTitle() );
		genreRelease.setText( "(" + movieDetails.getGenre() + ", " + movieDetails.getReleased() + ")" );
		plot.setText( movieDetails.getPlot() );
		
		if ( movieDetails.getPoster() != null ){
			
			poster.setScaleType( ImageView.ScaleType.CENTER_CROP );
			poster.setImageUrl( movieDetails.getPoster(), volleyImageLoader );
		}	
    }
	
	OnPostExecuteListener<MovieDetails> onPostListener = new OnPostExecuteListener<MovieDetails>() {

		@Override
		public void onFinish( MovieDetails movieDetails ) {
			
			doView( movieDetails );
			FragmentMovieDetails.this.movieDetails = movieDetails;
			progressBar.setVisibility( View.GONE );
		}
	};
	
	OnPreExecuteListener onPreListener = new OnPreExecuteListener() {

		@Override
		public void onFinish() {
			
			progressBar.setVisibility( View.VISIBLE );
		}
	};
	
}
