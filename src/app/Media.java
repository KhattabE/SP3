package app;

import app.util.UIText;

import java.util.ArrayList;

public abstract class Media {

    // UIText object so we can use it instead of System.out.print
    UIText ui = new UIText();

    // Class fields
    private String title;
    private ArrayList<String> genres;
    private int releaseYear;
    private double rating;
    private ArrayList<User> usersWhoHaveSeen;

    // Constructor to set up the basic media information
    public Media(String title, ArrayList<String> genres, int releaseYear, double rating) {
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    // Getters to make the fields readable
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

    // This method checks if the media has a genre that matches the search text
    public boolean matchesGenre(String genre) {
        String search = genre.trim().toLowerCase();
        for (String genre1 : genres) {
            if (genre1.trim().toLowerCase().contains(search)) {
                return true;
            }
        }
        return false;
    }

    // This method prints basic info about the media (title, year, rating, genres)
    public void displayInfo() {

        // Just a variable to collect all genre names
        String genreList = "";

        // For loop to go through each genre in the list
        for (int i = 0; i < genres.size(); i++) {

            // This will add the genre name directly
            genreList += genres.get(i);

            // This will add comma and space unless it's the last one
            if (i < genres.size() - 1) {
                genreList += ", ";
            }
        }

        ui.displayMsg("Media Title: " + this.title +
                "\nMedia Release Year: " + this.releaseYear +
                "\nMedia Rating: " + this.rating +
                "\nMedia Genres: " + genreList);
    }

    // This method simulates playing the media
    public void play(UIText ui) {
        ui.displayMsg("Now playing: " + title);
        ui.displayMsg(title + " is playing...");
        ui.displayMsg(title + " has finished playing.");
    }

    // This static method lets the user search for a movie or series by title
    public static void searchAfterMedia(UIText ui, ArrayList<Movie> movieList, ArrayList<Series> seriesList) {

        String mediaName = ui.promptText("Enter the name of the movie/series you are searching for: ");

        while (true) {
            boolean isFound = false;

            // Loops through the movie list
            for (Movie movie : movieList) {
                // Checks if the movie title equals what the user has written
                if (movie.getTitle().equalsIgnoreCase(mediaName)) {
                    ui.displayMsg("Movie Found!");
                    // Shows info about the movie
                    movie.displayInfo();
                    ui.displayMsg("");

                    String choice = ui.promptText("Type 0 to exit or type 1 to play " + mediaName);
                    if (choice.equals("1")) {
                        movie.play(ui);
                        ui.displayMsg("Returning to menu");
                    }
                    isFound = true;
                    break;
                }
            }

            // Loops through the series list
            for (Series series : seriesList) {
                // Checks if the series title equals what the user has written
                if (series.getTitle().equalsIgnoreCase(mediaName)) {
                    ui.displayMsg("Series Found!");
                    series.displayInfo();
                    ui.displayMsg("");

                    String choice = ui.promptText("Type 0 to exit or type 1 to play " + mediaName);
                    if (choice.equals("1")) {
                        series.play(ui);
                        ui.displayMsg("Returning to menu");
                    }
                    isFound = true;
                    break;
                }
            }

            // If no movie or series was found
            if (!isFound) {
                ui.displayMsg("");
                ui.displayMsg("No movie or series found with the given name!");
                String input = ui.promptText("Try again or type 0 to return to menu");

                // If the user wants to stop searching
                if (input.equals("0")) {
                    return;
                }
                // Otherwise, try to search again with the new input
                mediaName = input;
            } else {
                // If something was found, stop the loop
                break;
            }
        }
    }

    // This static method lets the user save a movie or series to the saved list
    public static void saveMedia(UIText ui, ArrayList<Movie> movieList, ArrayList<Series> seriesList, ArrayList<Media> savedList) {

        // Ask the user what type of media they want to save
        ui.displayMsg("Would you like to save a movie or a series?");
        ui.displayMsg("1: Save a movie");
        ui.displayMsg("2: Save a series");
        int choice = ui.promptNumeric("Enter your choice: ");

        boolean isFound = false; // This tracks if we found the media

        // If the user wants to save a movie
        if (choice == 1) {
            String name = ui.promptText("Enter the exact name of the movie you want to save: ");

            // Loop through all movies to find the correct one
            for (Movie movie : movieList) {
                if (name.equalsIgnoreCase(movie.getTitle())) {
                    ui.displayMsg("Movie found!");
                    savedList.add(movie);
                    ui.displayMsg("Movie saved successfully!");
                    isFound = true;
                    break;
                }
            }

            // If the user wants to save a series
        } else if (choice == 2) {

            String name = ui.promptText("Enter the exact name of the series you want to save: ");

            // Loop through all series to find the correct one
            for (Series series : seriesList) {
                if (name.equalsIgnoreCase(series.getTitle())) {
                    ui.displayMsg("Series found!");
                    savedList.add(series);
                    ui.displayMsg("Series saved successfully!");
                    isFound = true;
                    break;
                }
            }
        }

        // If nothing was found with that name
        if (!isFound) {
            ui.displayMsg("No Media exists with that name!");
        }
    }

}