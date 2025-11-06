package app;

import javax.print.DocFlavor;
import java.util.ArrayList;

public abstract class Media {

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

    public String getTitle() {
        return title;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void displayInfo(){
        // Displays the info
    }

    public boolean matchesGenre(String genre){
        return true;

        // checks if it matches genre
    }

    public boolean matchesTitle(String title){
        return true;
        // checks if it matches the title
    }


    public String toFileString(){
        return "";

        // Sends to file
    }




}