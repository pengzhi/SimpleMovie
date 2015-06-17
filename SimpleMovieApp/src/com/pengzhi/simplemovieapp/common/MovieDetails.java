package com.pengzhi.simplemovieapp.common;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDetails implements Parcelable {
	
	public static final String KEY = "MovieDetails";
	
	private String Title;
	private String Released;
	private String Genre;
	private String Plot;
	private String Poster;
	
	public MovieDetails( Parcel in ){
		
		if ( in == null ){ return; }
		
        Title = in.readString();
        Released = in.readString();
        Genre = in.readString();
        Plot = in.readString();
        Poster = in.readString();
	}	
	
	public String getTitle() {
		return Title;
	}

	public String getReleased() {
		return Released;
	}

	public String getGenre() {
		return Genre;
	}

	public String getPlot() {
		return Plot;
	}
	
	public String getPoster() {
		return Poster;
	}

	@Override
	public int describeContents() {
        return 0;
    }
	
   @Override
    public void writeToParcel(Parcel dest, int flags) {
        
        dest.writeString( Title );
        dest.writeString( Released );
        dest.writeString( Genre );
        dest.writeString( Plot );
        dest.writeString( Poster );
    }	
    
    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };
	
	@Override
	public String toString() {
		return "MovieDetails [Title=" + Title + ", Released=" + Released
				+ ", Genre=" + Genre + ", Plot=" + Plot + ", Poster=" + Poster
				+ "]";
	}
	
}
