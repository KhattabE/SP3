package app;

import app.util.UIText;

import java.util.ArrayList;

public class Movie extends Media  {

    //UIText object, so we can use them instead of "System.out.print"
    UIText ui = new UIText();


    //Class field
    private double movieLength;

    //Class Constructor
    public Movie(String title, ArrayList<String> genres, int releaseYear, double rating, UIText ui, double movieLength) {
        super(title, genres, releaseYear, rating);
        this.ui = ui;
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


    // Method to check if the movie rating is above a given value
    public boolean isRatedAbove(double isRating){

        // If the movies rating is higher than the given value
        if (this.getRating() > isRating){
            //This will show a message saying that the movie is rated above the given value
            ui.displayMsg("This movie is rated above " + isRating);
            return true;
        }
        //This will show a massage if the movie rating is not higher than the given value
        ui.displayMsg("This movie is NOT rated above " + isRating);
        return false;
    }


    @Override
    public void displayInfo() {

        //Just a variable to collect all genre names
        String genreList = "";

        // For loop to loop through each genre in the list
        for (int i = 0; i < getGenres().size(); i++) {

            //This will add the genre name to the genreList string
            genreList += getGenres().get(i);

            //This will make sure if this is not the last genre, it will add a comma and space
            if (i < getGenres().size() - 1) {
                genreList += ", ";
            }
        }
        ui.displayMsg("Movie Title: " + this.getTitle() +
                        "\nMovie Release Year: " + this.getReleaseYear() +
                        "\nMovie Rating: " + this.getRating() +
                        "\nMovie Genres: " + genreList +
                        "\nMovie length: " + this.movieLength);

    }


}
