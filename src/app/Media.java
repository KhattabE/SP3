package app;

import app.util.UIText;

import java.util.ArrayList;

public abstract class Media implements MedieFunctions {

    //UIText object, so we can use them instead of "System.out.print"
    UIText ui = new UIText();

    //Class fields
    private String title;
    private ArrayList<String> genres;
    private int releaseYear;
    private double rating;
    private ArrayList<User> usersWhoHaveSeen;

    public Media(String title, ArrayList<String> genres, int releaseYear, double rating) {
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }


    //Getters to make the fields readable
    public String getTitle() {
        return title;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }

    //Setter to make the fields writable
    public void setRating(double rating) {
        this.rating = rating;
    }

    //Method for checking if the media contains the given genre (this will be used for filtering/search)
    public boolean matchesGenre(String genre) {
        for (String genre1 : genres) {
            if (genre1.equalsIgnoreCase(genre)) {
                ui.displayMsg("There is a genre match!");
                return true;
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

            //This will add the genre name directly
            genreList += genres.get(i);

            // This will add comma and space unless its the last one
            if (i < genres.size() - 1) {
                genreList += ", ";
            }
        }

        ui.displayMsg("Media Title: " + this.title +
                        "\nMedia Release Year: " + this.releaseYear +
                        "\nMedia Rating: " + this.rating +
                        "\nMedia Genres: " + genreList);
    }


    public String toFileString() {

        //We start with title and release year, separated by semicolon
        String fileLine = title + ";" + releaseYear + ";";

        //this loops through each genre and add the genre name to the line
        for (String g : genres) {
            fileLine += g + ";";
        }

        //this adds the rating at the end followed by semicolon
        fileLine += rating + ";";

        //This returns the full line
        return fileLine;
    }




    @Override
    public void medieIsPlaying(){
        ui.displayMsg("The Media is now playing...");
    }

    @Override
    public void medieIsPaused(){
        ui.displayMsg("The Media has been paused...");
    }

    @Override
    public void medieIsEnded(){
        ui.displayMsg("The Media has been ended...");
    }







    }