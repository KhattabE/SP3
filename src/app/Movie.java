package app;

import app.util.UIText;

import java.util.ArrayList;

public class Movie extends Media  {

    //UIText object, so we can use them instead of "System.out.print"
    UIText ui = new UIText();


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

    //Method that will check if the movie is a long movie
    public boolean isLongMovie(){
        if(movieLength > 120){
            ui.displayMsg("This is a long movie!");
            return true;
        }
            ui.displayMsg("This is an average length movie!");
        return false;

    }

    //Method that will check if the movie is a short movie
    public boolean isShortMovie(){
        if(movieLength <= 40){
            ui.displayMsg("This is a short movie!");
            return true;
        }
        ui.displayMsg("This is not a short movie!");
        return false;
    }






}
