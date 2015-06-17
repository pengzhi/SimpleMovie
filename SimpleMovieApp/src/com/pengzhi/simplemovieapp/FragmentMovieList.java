package com.pengzhi.simplemovieapp;

import com.pengzhi.simplemovieapp.common.Movie;
import com.pengzhi.simplemovieapp.common.SearchResults;
import com.pengzhi.simplemovieapp.task.BaseTask.OnPostExecuteListener;
import com.pengzhi.simplemovieapp.task.BaseTask.OnPreExecuteListener;
import com.pengzhi.simplemovieapp.task.SearchMovieTask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FragmentMovieList extends Fragment {
	
    protected static final String TAG = "FragmentMovieList";

	private static final String LAST_SEARCH = "lastSearch";
    
    private SearchResultsAdapter searchResultsAdapter;
    private MovieDetailsPasser movieDetailsPasser;
    private SearchResults mSearchResults;
    private EditText searchField;
    private String mLastSearch = "";
    private ProgressBar progressBar;
    
	@Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	View view = inflater.inflate( R.layout.fragment_movie_list, container, false );
    	searchField = (EditText) view.findViewById( R.id.search_field );
    	ListView searchResults = (ListView) view.findViewById( R.id.search_results );
    	TextView emptyText = (TextView) view.findViewById( R.id.empty );
    	progressBar = (ProgressBar) view.findViewById( R.id.progress_bar );
    	
    	searchResultsAdapter = new SearchResultsAdapter( getActivity() );
    	
    	searchResults.setOnItemClickListener( onItemClickListener );
    	searchResults.setAdapter( searchResultsAdapter );
    	searchResults.setEmptyView( emptyText );
    	
    	if (savedInstanceState != null) {
    		
            mSearchResults = (SearchResults) savedInstanceState.getParcelable( SearchResults.KEY );
            mLastSearch = savedInstanceState.getString( LAST_SEARCH, "");
            
            if ( mSearchResults != null )
            	searchResultsAdapter.addAll( mSearchResults.getSearch() );
        }
    	
    	return view;
    }
	
	
    @Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		
    	super.onActivityCreated(savedInstanceState);
    	
    	// to prevent textwatcher being called when orientation changes
    	// but somehow didn't work, have to use mLastSearch to determine if
    	// search results were returned prior to orientation change
    	searchField.addTextChangedListener( searchFieldWatcher );
	}


	@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	
        super.onSaveInstanceState(savedInstanceState);
        
        savedInstanceState.putParcelable( SearchResults.KEY, mSearchResults );
        savedInstanceState.putString( LAST_SEARCH, mLastSearch );
    }	
	
    @Override
    public void onAttach(Activity activity) {
        
        super.onAttach(activity);
        
        // Verify that the host activity implements the callback interface
        try {
            
        	movieDetailsPasser = (MovieDetailsPasser) getActivity();
        	
        } catch (ClassCastException e) {
            
            throw new ClassCastException( getActivity().toString() + " must implement MovieDetailsPasser");
        }
    }  	
    
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			Movie movie = searchResultsAdapter.getItem( position );
			movieDetailsPasser.show(movie);
			hideSoftKeyboard( getActivity() );
		}
	}; 
    
	private TextWatcher searchFieldWatcher = new TextWatcher() {
		
		@Override
		public void afterTextChanged(final Editable e) {
			
			Log.d(TAG, "afterTextChanged editable: " + e.toString());
			
			SearchMovieTask searchTask = new SearchMovieTask( e.toString() );
			searchTask.setOnPreExecuteListener( new OnPreExecuteListener() {

				@Override
				public void onFinish() {
					
					progressBar.setVisibility( View.VISIBLE );
				}
			});
			searchTask.setOnPostExecuteListener( new OnPostExecuteListener<SearchResults>() {

				@Override
				public void onFinish( SearchResults searchResults ) {
					
					progressBar.setVisibility( View.GONE );
					
					if ( searchResults != null && searchResults.getSearch() != null ){
						
						Log.d(TAG, searchResults.toString());
						searchResultsAdapter.clear();
						searchResultsAdapter.addAll( searchResults.getSearch() );
						
						mSearchResults = searchResults;
						mLastSearch = e.toString();
						
					} else {
						
						searchResultsAdapter.clear();
					}
				}
			});
			
			if ( !mLastSearch.equals( e.toString() ) ){
				searchTask.execute();
			}
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	}; 
	
	public static class SearchResultsAdapter extends ArrayAdapter<Movie>{
		
		private static class ViewHolder {
			
			TextView title;
		}
		
		public SearchResultsAdapter( Context context ) {
			super(context, 0);
		}
		
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder;
			
			if (convertView == null) {
				
				convertView = LayoutInflater.from(getContext()).inflate( R.layout.search_results_item, null );
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById( R.id.title );
                
                convertView.setTag( holder );
                
			} else {
				
				holder = (ViewHolder) convertView.getTag();
			}
			
			Movie movie = getItem( position );
			
			holder.title.setText( movie.getTitle() );
			
			return convertView;
		}
		
	}
	
    public static interface MovieDetailsPasser {
        
        public void show( Movie movie ); 
    }
    
    private void hideSoftKeyboard( Context context ){
        
    	InputMethodManager inputManager = (InputMethodManager) context
    			.getSystemService(Context.INPUT_METHOD_SERVICE);

        View v = ((Activity) context).getCurrentFocus();
        
        if ( v == null )
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        v.clearFocus();
    }    
}
