package app;

import app.util.UIText;

import java.util.ArrayList;

public abstract class Media {

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

    // This method returns the type of the media (Movie or Series)
    public abstract String getType();



    public boolean matchesGenre(String genre) {
        String search = genre.trim().toLowerCase();
        for (String genre1 : genres) {
            if (genre1.trim().toLowerCase().contains(search)) {
                return true;
            }
        }
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



    public void play(UIText ui) {
        ui.displayMsg("Now playing: " + title);
        ui.displayMsg(title + " is playing...");
        ui.displayMsg(title + " has finished playing.");
    }











    }