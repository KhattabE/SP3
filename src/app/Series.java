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

    // Getters for readability
    public int getSeasons() {
        return seasons;
    }

    public int getEpisodes() {
        return episodes;
    }

    public int getEpisodeLength() {
        return episodeLength;
    }

    // Setters for writability
    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public void setEpisodeLength(int episodeLength) {
        this.episodeLength = episodeLength;
    }

    //Method that will check if the series is a short series with under 1 season and under 4 episodes
    public boolean isMiniSeries(){
       if (seasons <= 1 && episodes <= 4){
           ui.displayMsg("This is a mini series");
            return true;

       }
       ui.displayMsg("This is a normal series");
       return false;

    }
    // Method to check if the series rating is above a given value
    public boolean isRatedAbove(double isRating){

        // If the sereies rating is higher than the given value
        if (this.getRating() > isRating){
            //This will show a message saying that the series is rated above the given value
            ui.displayMsg(getTitle() + " is rated above " + isRating);
            return true;
        }
        //This will show a massage if the series rating is not higher than the given value
        ui.displayMsg(getTitle() + " is rated under " + isRating);
        return false;
    }



    //Overriding the medieIs... methods that the subclasses inherited from the parent class, and which the parent class implemented from the interface classes
    @Override
    public void medieIsPlaying(){
        ui.displayMsg("The series is now playing...");
    }

    @Override
    public void medieIsPaused(){
        ui.displayMsg("The series has been paused...");
    }

    @Override
    public void medieIsEnded(){
        ui.displayMsg("The series has been ended...");
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
