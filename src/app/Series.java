package app;

import app.util.FileIO;
import app.util.UIText;

import java.util.ArrayList;

public class Series extends Media  {

    // UIText object so we can use it instead of System.out.print
    UIText ui = new UIText();

    // Class fields
    private int seasons;
    private int episodes;
    private int episodeLength;

    // Constructor for setting up all series information
    public Series(String title, ArrayList<String> genres, int releaseYear, double rating,
                  int seasons, int episodes, int episodeLength) {
        super(title, genres, releaseYear, rating);
        this.seasons = seasons;
        this.episodes = episodes;
        this.episodeLength = episodeLength;
    }

    // Returns the type of this media (important for saving to CSV)
    @Override
    public String getType() {
        return "Series";   // FIXED bug (previously returned "Movie")
    }

    // Displays all series information
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

        ui.displayMsg("Series Title: " + this.getTitle() +
                "\nSeries Release Year: " + this.getReleaseYear() +
                "\nSeries Rating: " + this.getRating() +
                "\nSeries Genres: " + genreList +
                "\nEpisode Length: " + this.episodeLength +
                "\nNumber Of Seasons: " + this.seasons +
                "\nNumber Of Episodes: " + this.episodes);
    }

    // Loads all series from a CSV file and stores them in the provided list
    public static void loadSeriesFromFile(FileIO io, String seriesFilePath,
                                          ArrayList<Series> seriesList, UIText ui) {

        // Read all lines from the series CSV
        ArrayList<String> lines = io.readData(seriesFilePath);

        // Process each CSV line
        for (String line : lines) {

            // Split CSV line into parts
            String[] parts = line.split(";");

            // Ensure the line contains enough data
            if (parts.length < 3) continue;

            // Read title
            String title = parts[0].trim();

            // Read release year
            int year = 0;
            try {
                String yearText = parts[1].trim();

                // Handle formatted years like "2010-2013"
                if (yearText.contains("-")) {
                    yearText = yearText.split("-")[0].trim();
                }

                year = Integer.parseInt(yearText);
            } catch (Exception e) {
                continue; // Skip invalid year
            }

            // Read genres
            ArrayList<String> genres = new ArrayList<>();
            for (int i = 2; i < parts.length - 2; i++) {
                String genre = parts[i].trim();
                if (!genre.isEmpty()) {
                    genres.add(genre);
                }
            }

            // Read rating
            double rating = 0.0;
            try {
                String ratingText = parts[parts.length - 2].trim().replace(",", ".");
                rating = Double.parseDouble(ratingText);
            } catch (Exception e) {
                // Keep default rating if parsing fails
            }

            // Last column is supposed to contain episode/season info
            String episodeData = parts[parts.length - 1].trim();
            int seasons = 0;
            int episodes = 0;

            // Create Series object
            Series series = new Series(title, genres, year, rating, seasons, episodes, 0);

            // Add to list
            seriesList.add(series);
        }

        ui.displayMsg("Loaded " + seriesList.size() + " series from file!");
    }

    // Prints all series directly from the CSV file
    public static void listAllSeriesFromFile(FileIO io, String seriesFilePath, UIText ui) {

        // Read all lines from Series CSV
        ArrayList<String> lines = io.readData(seriesFilePath);

        // Handle empty file
        if (lines.isEmpty()) {
            ui.displayMsg("No series found.");
            return;
        }

        ui.displayMsg("Series from file:");
        for (String line : lines) {
            ui.displayMsg(" - " + line);
        }
    }

    // Allows the user to choose a series to play or return back
    public static void chooseSeriesFromList(UIText ui, ArrayList<Series> seriesList,
                                            ArrayList<Media> seenList) {

        ui.displayMsg("All of the available series:");

        // Print all series titles
        for (Series s : seriesList) {
            ui.displayMsg(" - " + s.getTitle());
        }

        String choice = ui.promptText("Type 1 to choose a series or 0 to return: ");

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

        // Ask for exact series title
        String userChoice = ui.promptText("Enter the exact name of the series: ");
        boolean isFound = false;

        // Search for a match
        for (Series s : seriesList) {
            if (s.getTitle().equalsIgnoreCase(userChoice)) {
                s.play(ui);
                seenList.add(s);
                isFound = true;
                break;
            }
        }

        // If no match found
        if (!isFound) {
            ui.displayMsg("The series does not exist.");
        }

        ui.promptText("Type 0 to return to menu: ");
    }
}