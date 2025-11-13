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


    // This method returns the type of the media
    @Override
    public String getType() {
        return "Movie";
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
