package com.pengzhi.simplemovieapp.common;

import java.io.IOException;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public enum Server {
	
	INSTANCE;
	
	private static final OkHttpClient client = new OkHttpClient();
	private static final Gson gson = new Gson();
	
	public SearchResults searchMovies( String searchString ) throws Exception {
		
		String query = URLEncoder.encode( searchString, "utf-8" );
		
		Request request = new Request.Builder()
        	.url( "http://www.omdbapi.com/?s=" + query )
        	.build();
		Response response = client.newCall(request).execute();
		
		if ( !response.isSuccessful() ) throw new IOException( "Unexpected code " + response );

		SearchResults searchResults = gson.fromJson( response.body().charStream(), SearchResults.class );
		
		return searchResults;
	}
	
	public MovieDetails getMovieDetails( String imdbID ) throws Exception {
		
		Request request = new Request.Builder()
        	.url( "http://www.omdbapi.com/?i=" + imdbID + "&plot=short&r=json" )
        	.build();
		Response response = client.newCall(request).execute();
		
		if ( !response.isSuccessful() ) throw new IOException( "Unexpected code " + response );

		MovieDetails details = gson.fromJson( response.body().charStream(), MovieDetails.class );
		
		return details;
	}	
}
