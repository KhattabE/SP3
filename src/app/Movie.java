package app;

import java.util.ArrayList;

public class Movie extends Media  {

    //Class field
    private double movieLength;


    //Class Constructor
    public Movie(String title, ArrayList<Genre> genres, int releaseYear, double rating, double movieLength) {
        //Super keyword to inherit the fields from the parent class
        super(title, genres, releaseYear, rating);
        this.movieLength = movieLength;
    }


    //Getters
    public double getMovieLength() {
        return movieLength;
    }

    //Setters
    public void setMovieLength(double movieLength) {
        this.movieLength = movieLength;
    }

    //Overriding the medieIs... methods that the subclasses inherited from the parent class, and which the parent class implemented from the interface classes
    @Override
    public void medieIsPlaying(){
        ui.displayMsg("The movie is now playing...");
    }

    @Override
    public void medieIsPaused(){
        ui.displayMsg("The movie has been paused...");
    }

    @Override
    public void medieIsEnded(){
        ui.displayMsg("The movie has been ended...");
    }






}
