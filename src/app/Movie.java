package app;

import app.util.FileIO;
import app.util.UIText;

import java.util.ArrayList;

public class Movie extends Media  {

    // UIText object so we can use it instead of System.out.print
    UIText ui = new UIText();

    // Class field for movie length
    private double movieLength;

    // Constructor for setting up all movie information
    public Movie(String title, ArrayList<String> genres, int releaseYear, double rating, UIText ui, double movieLength) {
        super(title, genres, releaseYear, rating);
        this.ui = ui;
        this.movieLength = movieLength;
    }

    // Returns the type of this media (used when saving)
    @Override
    public String getType() {
        return "Movie";
    }

    // Displays all movie information
    @Override
    public void displayInfo() {

        // Build a single string containing all genres
        String genreList = "";

        // Loop through all genres and format them with commas
        for (int i = 0; i < getGenres().size(); i++) {
            genreList += getGenres().get(i);

            // Add comma unless it is the last genre
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

    // Loads all movies from a CSV file and stores them in the provided list
    public static void loadMoviesFromFile(FileIO io, String movieFilePath, ArrayList<Movie> movieList, UIText ui) {

        // Read all lines from the CSV file
        ArrayList<String> lines = io.readData(movieFilePath);

        // Go through every line in the file
        for (String line : lines) {

            // Split the CSV line into parts
            String[] parts = line.split(";");

            // Ensure the line contains enough data
            if (parts.length < 4) continue;

            // Read the movie title
            String title = parts[0].trim();

            // Read the release year
            int year = 0;
            try {
                year = Integer.parseInt(parts[1].trim());
            } catch (Exception e) {
                continue; // Skip this movie if year is invalid
            }

            // Read the rating
            double rating = 0.0;
            try {
                String ratingText = parts[parts.length - 1].trim().replace(",", ".");
                rating = Double.parseDouble(ratingText);
            } catch (Exception e) {
                // Keep rating at default 0.0 if parsing fails
            }

            // Collect all genres
            ArrayList<String> genres = new ArrayList<>();
            for (int i = 2; i < parts.length - 1; i++) {
                String genre = parts[i].trim();
                if (!genre.isEmpty()) {
                    genres.add(genre);
                }
            }

            // Create the Movie object
            Movie movie = new Movie(title, genres, year, rating, ui, 0);

            // Add to memory list
            movieList.add(movie);
        }

        ui.displayMsg("Loaded " + movieList.size() + " movies from file!");
    }

    // Prints all movies directly from the CSV file
    public static void listAllMoviesFromFile(FileIO io, String movieFilePath, UIText ui) {

        // Read all lines from movie CSV
        ArrayList<String> lines = io.readData(movieFilePath);

        // Handle empty file
        if (lines.isEmpty()) {
            ui.displayMsg("No Movies found.");
            return;
        }

        ui.displayMsg("Movies from file:");
        for (String line : lines) {
            ui.displayMsg(" - " + line);
        }
    }

    // Allows the user to choose a movie from the list or return back
    public static void chooseMovieFromList(UIText ui, ArrayList<Movie> movieList, ArrayList<Media> seenList) {

        ui.displayMsg("All of the available movies:");

        // Print all movie titles
        for (Movie m : movieList) {
            ui.displayMsg(" - " + m.getTitle());
        }

        String choice = ui.promptText("Type 1 to choose a movie or 0 to return: ");

        // Immediate return
        if (choice.equals("0")) {
            ui.displayMsg("Returning to menu");
            return;
        }

        // Validate input
        while (!choice.equals("1") && !choice.equals("0")) {
            choice = ui.promptText("Invalid choice, please type 1 or 0: ");
        }

        if (choice.equals("0")) {
            ui.displayMsg("Returning to main menu");
            return;
        }

        // Ask for the movie title
        String userChoice = ui.promptText("Enter the exact name of the movie: ");
        boolean isFound = false;

        // Search for the movie
        for (Movie m : movieList) {
            if (m.getTitle().equalsIgnoreCase(userChoice)) {
                m.play(ui);
                seenList.add(m);
                isFound = true;
                break;
            }
        }

        // Handle if no movie was found
        if (!isFound) ui.displayMsg("The movie does not exist.");

        ui.promptText("Type 0 to return to menu: ");
    }
}