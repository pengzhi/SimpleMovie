package com.pengzhi.simplemovieapp.task;

import com.pengzhi.simplemovieapp.common.SearchResults;
import com.pengzhi.simplemovieapp.common.Server;

public class SearchMovieTask extends BaseTask<Void, SearchResults, SearchResults> {
	
	private String searchString;

	public SearchMovieTask( String searchString ){
		
		this.searchString = searchString;
	}
	
	@Override
	protected SearchResults doInBackground(Void... params) {
		
		try {
			
			return Server.INSTANCE.searchMovies(searchString);
			
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
    protected void onPostExecute( SearchResults searchResults ) {
        
        if (onPostExecuteListener != null){
            onPostExecuteListener.onFinish( searchResults );
        }
    } 	   
}
