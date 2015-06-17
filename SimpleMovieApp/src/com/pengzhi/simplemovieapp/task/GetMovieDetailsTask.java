package com.pengzhi.simplemovieapp.task;

import com.pengzhi.simplemovieapp.common.MovieDetails;
import com.pengzhi.simplemovieapp.common.Server;

public class GetMovieDetailsTask extends BaseTask<Void, MovieDetails, MovieDetails> {
	
	private String imdbID;

	public GetMovieDetailsTask( String imdbID ){
		
		this.imdbID = imdbID;
	}
	
	@Override
	protected MovieDetails doInBackground(Void... params) {
	
		try {
			
			return Server.INSTANCE.getMovieDetails( imdbID );
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
			
		}		
	}
	
   @Override
    protected void onPreExecute() {
        
        if (onPreExecuteListener != null){
            onPreExecuteListener.onFinish();
        }
    } 
   
    @Override
    protected void onPostExecute( MovieDetails movieDetails ) {
        
        if (onPostExecuteListener != null){
            onPostExecuteListener.onFinish( movieDetails );
        }
    } 	

}
