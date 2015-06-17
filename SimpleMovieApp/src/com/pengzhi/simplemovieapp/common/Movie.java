package com.pengzhi.simplemovieapp.common;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
	
	public static final String KEY = "Movie";
	
	String Title;
	String Year;
	String imdbID;
	String movie;

	public Movie( Parcel in ){
		
		if ( in == null ){ return; }
		
        Title = in.readString();
        Year = in.readString();
        imdbID = in.readString();
        movie = in.readString();
	}	
	
	@Override
	public String toString() {
		return "Movie [Title=" + Title + ", Year=" + Year + ", imdbID="
				+ imdbID + ", movie=" + movie + "]";
	}

	public String getTitle() {
		return Title;
	}
	
	public String getImdbID() {
		return imdbID;
	}
	
	@Override
	public int describeContents() {
        return 0;
    }
	
   @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString( Title );
        dest.writeString( Year );
        dest.writeString( imdbID );
        dest.writeString( movie );
    }	
    
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };		
}
