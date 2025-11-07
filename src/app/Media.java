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








}