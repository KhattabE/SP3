package app;

import java.util.ArrayList;

public class Movie extends Media  {
    private int movieLength;

    public Movie(String title, ArrayList<Genre> genres, int releaseYear, double rating, int movieLength) {
        super(title, genres, releaseYear, rating);
        this.movieLength = movieLength;
    }

    public int getMovieLength(){
        // Shows the movies length
        return 0;
    }

    public void setMovieLength(int length){
        // Sets movie length
    }

    public boolean isLongMovie(){
        return true;
        // If movie is x long then true
    }

    public boolean isRatedAbove(double isRating){
        return true;

        // If rating is x then true

    }
}
