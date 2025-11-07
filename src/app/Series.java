package app;

import java.util.ArrayList;

public class Series extends Media implements MediaPlay, MediaPause, MediaEnd {

    private int seasons;
    private int episodes;
    private double episodeLength;

    public Series(String title, ArrayList<Genre> genres, int releaseYear, double rating, int seasons, int episodes, double episodeLenght) {
        super(title, genres, releaseYear, rating);
        this.seasons = seasons;
        this.episodes = episodes;
        this.episodeLength = episodeLength;
    }


    public int getSeasons(){
        // Getter for showing seasons
    }

    public int getEpisodes(){
        // Getter for showing episodes
    }

    public int getEpisodeLength(){
        // Getter for showing episode length
    }

    public void setSeasons(int seasons){
        // set seasons
    }

    public void setEpisodes(int episodes){
        // set episodes
    }

    public void setEpisodeLength(int length){
        // set episode length
    }

    public boolean isMiniSeries(){
        // true if this is a mini series
        return true;
    }

    public boolean isRatedAbove(double isRating){
        // true if this is rated above x
        return true;
    }

}
