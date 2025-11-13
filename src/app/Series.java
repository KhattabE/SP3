package app;

import app.util.UIText;

import java.util.ArrayList;

public class Series extends Media  {

    //UIText object, so we can use them instead of "System.out.print"
    UIText ui = new UIText();

    // class fields
    private int seasons;
    private int episodes;
    private int episodeLength;

    //class construcor
    public Series(String title, ArrayList<String> genres, int releaseYear, double rating, int seasons, int episodes, int episodeLength) {
        super(title, genres, releaseYear, rating);
        this.seasons = seasons;
        this.episodes = episodes;
        this.episodeLength = episodeLength;
    }


    // This method returns the type of the media
    @Override
    public String getType() {
        return "Movie";
    }


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
        ui.displayMsg("Series Title: " + this.getTitle() +
                "\nSeries Release Year: " + this.getReleaseYear() +
                "\nSeries Rating: " + this.getRating() +
                "\nSeries Genres: " + genreList +
                "\nSeries length: " + this.episodeLength +
                "\nNumber Of Seasons: " + this.seasons +
                "\nNumber Of Episodes: " + this.episodes);

    }






}
