package app;

import app.util.UIText;

import javax.print.DocFlavor;
import java.util.ArrayList;

public abstract class Media {

    //UIText object, so we can use them instead of "System.out.print"
    UIText ui = new UIText();

    //Class fields
    private String title;
    private ArrayList<Genre> genres;
    private int releaseYear;
    private double rating;
    private ArrayList<User> usersWhoHaveSeen;

    public Media(String title, ArrayList<Genre> genres, int releaseYear, double rating) {
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }


    //Getters to make the fields readable
    public String getTitle() {
        return title;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }


    //Setter to make the fields writable
    public void setRating(double rating) {
        this.rating = rating;
    }

    //Method for checking if the media contains the given genre (this will be used for filtering/search)
    public boolean matchesGenre(String genre){
        for (Genre genre1 : genres){
            if (genre1.getGenreName().equalsIgnoreCase(genre)){
                ui.displayMsg("There is a genre match!");
                return  true;
            }
        }
        ui.displayMsg("There is no genre match!");
        return false;
    }

    // Method for checking if the media title contains the given search string (This will be used for title search)
    public boolean matchesTitle(String titles){

        if (this.title.toLowerCase().contains(titles.toLowerCase())){ //This will even match, if you write "Titan" instead of "Titanic"
            ui.displayMsg("Title match found!");
            return true;
        }

        ui.displayMsg("No title match found!");
        return false;
    }


    public void displayInfo() {

        //Just a variable to collect all genre names
        String genreList = "";

        // For loop to loop through each genre in the list
        for (int i = 0; i < genres.size(); i++) {

            //This will add the genre name to the genreList string
            genreList += genres.get(i).getGenreName();

            //This will make sure if this is not the last genre, it will add a comma and space
            if (i < genres.size() - 1) {
                genreList += ", ";
            }
        }
        ui.displayMsg(
                "Media Title: " + this.title +
                        "\nMedia Release Year: " + this.releaseYear +
                        "\nMedia Rating: " + this.rating +
                        "\nMedia Genres: " + genreList
        );
    }






}