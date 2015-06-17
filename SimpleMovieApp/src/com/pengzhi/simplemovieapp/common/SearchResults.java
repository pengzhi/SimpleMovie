package com.pengzhi.simplemovieapp.common;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchResults implements Parcelable {
	
	public static final String KEY = "SearchResults";
	private ArrayList<Movie> Search;
	
	public SearchResults( Parcel in ){
	
		 Search = new ArrayList<Movie>();
		 in.readList( Search, Movie.class.getClassLoader() );
	}
	
	@Override
	public String toString() {
		return "SearchResults [Search=" + Search + "]";
	}

	public ArrayList<Movie> getSearch() {
		return Search;
	}
	
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	
    	dest.writeList( Search );
    }
    
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public static final Creator<SearchResults> CREATOR = new Creator<SearchResults>() {
        public SearchResults createFromParcel(Parcel in) {
            return new SearchResults(in);
        }

        public SearchResults[] newArray(int size) {
            return new SearchResults[size];
        }
    };    
}
