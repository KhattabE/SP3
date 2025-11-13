package app;

import app.util.UIText;
import app.util.FileIO;

import java.util.ArrayList;

public class MainMenu {

    // Reference to a Media object (not used yet, but kept for future use)
    private Media media;

    // File paths for storing users, movies, series, saved and seen media
    private String userFilePath = "data/users.csv";
    private String movieFilePath = "movies.csv";
    private String seriesFilePath = "series.csv";
    private String savedMediaFilePath = "data/savedMedia.csv";
    private String seenMediaFilePath = "data/seenMedia.csv";

    // These are lists to hold all of the movies, series, and users
    private ArrayList<Movie> movieList;
    private ArrayList<Series> seriesList;
    private ArrayList<User> users;
    private ArrayList<Media> savedList;
    private ArrayList<Media> seenList;

    // This keeps track of the user currently logged in
    private User currentUser;

    // Used to handle input and output
    private UIText ui;

    // File I/O helper
    private final FileIO io = new FileIO();

    // Constructor
    public MainMenu(String userFilePath, String movieFilePath, String seriesFilePath) {
        this.userFilePath = userFilePath;
        this.movieFilePath = movieFilePath;
        this.seriesFilePath = seriesFilePath;
        this.seenMediaFilePath = "data/seenMedia.csv";

        // Initialize all of the lists and the UI
        this.ui = new UIText();
        this.movieList = new ArrayList<>();
        this.seriesList = new ArrayList<>();
        this.users = new ArrayList<>();
        this.currentUser = null;
        this.savedList = new ArrayList<>();
        this.seenList = new ArrayList<>();
    }

    // The main menu shown when the program starts
    public void show() {
        loadUsersFromFile();   // Load users from CSV at start
        loadMoviesFromFile();  // Load movies from CSV at start
        loadSeriesFromFile();  // Load series from CSV at start

        boolean isRunning = true;
        while (isRunning) {
            ui.displayMsg("\nWelcome to the streaming service AEMK Entertainments!");
            ui.displayMsg("You got the following options you can choose from: ");
            ui.displayMsg("1: Login");
            ui.displayMsg("2: Create an account");
            ui.displayMsg("0: Exit");

            int choice = ui.promptNumeric("Enter your choice: ");

            // A switch case to handle the user's choice
            switch (choice) {
                case 1 -> currentUser = User.login(ui, users);        // Login to an existing account
                case 2 -> currentUser = User.createAccount(ui, users); // Create a new user account
                case 0 -> isRunning = false;                          // Exit the program
                default -> ui.displayMsg("Invalid choice, please try again.");
            }

            // If a user is logged in, open the user menu
            if (currentUser != null) {
                loadSavedMediaFromFile(); // Load saved media for the current user
                loadSeenMediaFromFile();  // Load seen media for the current user
                showUserMenu();
            }
        }
    }

    // This method loads users from a file using FileIO
    public void loadUsersFromFile() {
        User.loadUsersFromFile(io, userFilePath, users, ui);
    }

    // This method saves all users in memory to the users file
    public void saveUsersToFile() {
        User.saveUsersToFile(io, userFilePath, users);
    }

    // This is the menu shown after a user has logged in
    public void showUserMenu() {

        while (true) {
            ui.displayMsg("\nWelcome, " + currentUser.getName());
            ui.displayMsg("1: List all movies");
            ui.displayMsg("2: List all series");
            ui.displayMsg("3: Search for a movie/series");
            ui.displayMsg("4: Search all movies/series from a genre");
            ui.displayMsg("5: Save movies/series");
            ui.displayMsg("6: List over saved movies/series");
            ui.displayMsg("7: List over seen movies/series");
            ui.displayMsg("8: Log-out and return to user menu");
            ui.displayMsg("0: Exit AEMK Entertainments");

            // Ask the user for a choice
            int choice = ui.promptNumeric("Enter your choice: ");

            switch (choice) {
                case 1 -> {
                    // List movies and let the user choose one
                    chooseMovieFromList();
                }

                case 2 -> {
                    // List series and let the user choose one
                    chooseSeriesFromList();
                }

                case 3 -> {
                    // Search for a specific movie or series by title
                    searchAfterMedia();
                }

                case 4 -> {
                    // Search for all media from a specific genre
                    searchAfterSameGenreMedia();
                }

                case 5 -> {
                    // Save a movie or series to the saved list
                    savedMediaList();

                    String input = ui.promptText("Type 0 to return to menu: ");
                    while (!input.equals("0"))
                        input = ui.promptText("Please type 0 to return to menu: ");
                }

                case 6 -> {
                    // Show list of saved media
                    listOverSavedMedia();

                    String input = ui.promptText("Type 0 to return to menu: ");
                    while (!input.equals("0"))
                        input = ui.promptText("Please type 0 to return to menu: ");

                }
                case 7 -> {
                    // Show list of seen media
                    listOverSeenMedia();
                    String input = ui.promptText("Type 0 to return to menu: ");
                    while (!input.equals("0"))
                        input = ui.promptText("Please type 0 to return to menu: ");

                }

                case 8 -> {
                    // Log out and go back to main menu
                    saveSavedMediaToFile();
                    saveSeenMediaToFile();
                    ui.displayMsg("Logging out and returning to user menu");
                    return;
                }

                case 0 -> {
                    // Exit the whole program
                    ui.displayMsg("Closing AEMK Entertainments!");
                    currentUser = null; // No user is logged in anymore
                    saveSavedMediaToFile();
                    saveSeenMediaToFile();
                    System.exit(0); // Close program
                }

                default -> ui.displayMsg("Invalid choice, please try again.");
            }
        }
    }

    // This method shows all seen media for the current user
    public void listOverSeenMedia() {
        if (seenList.isEmpty()) {
            ui.displayMsg("You have not watched any movies or series yet");
            return;
        }
        ui.displayMsg("Seen media:");
        for (Media media : seenList) {
            ui.displayMsg("");
            media.displayInfo();
        }
    }

    // This method lets the user choose a movie from the list
    public void chooseMovieFromList() {
        Movie.chooseMovieFromList(ui, movieList, seenList);
    }

    // This method is for searching for a movie or a series by title
    public void searchAfterMedia() {
        Media.searchAfterMedia(ui, movieList, seriesList);
    }

    // This method lets the user choose a series from the list
    public void chooseSeriesFromList() {
        Series.chooseSeriesFromList(ui, seriesList, seenList);
    }

    // This method is to find all media under a searched genre
    public void searchAfterSameGenreMedia() {
        String genreName = ui.promptText("Enter the genre, to see all media from that genre: ");

        while (true) {
            boolean genreIsFound = false;

            ui.displayMsg("");

            // Showing all movies that match the genre
            for (Movie movie : movieList) {
                if (movie.matchesGenre(genreName)) {
                    if (!genreIsFound) {
                        ui.displayMsg("**** All movies and series in " + genreName + " *****");
                        ui.displayMsg("");
                        genreIsFound = true;
                    }
                    movie.displayInfo();
                    ui.displayMsg("");
                }
            }

            // Showing all series that match the genre
            for (Series series : seriesList) {
                if (series.matchesGenre(genreName)) {
                    if (!genreIsFound) {
                        ui.displayMsg("**** All series and movies in " + genreName + " *****");
                        ui.displayMsg("");
                        genreIsFound = true;
                    }
                    series.displayInfo();
                    ui.displayMsg("");
                }
            }

            // If no series or movie is found
            if (!genreIsFound) {
                ui.displayMsg("No movies or series found with the genre: " + genreName);
                String input = ui.promptText("Try again or type 0 to return to menu");
                if (input.equals("0")) return;
                genreName = input;
                continue;
            }

            // Asks user which one to play
            while (true) {
                String seleact = ui.promptText("Type the exact title you want to play or 0 to return to menu");
                if (seleact.equals("0")) return;

                boolean foundMatch = false;

                // Playing the movie
                for (Movie movie : movieList) {
                    if (movie.getTitle().equalsIgnoreCase(seleact) && movie.matchesGenre(genreName)) {
                        movie.play(ui);
                        ui.displayMsg("Returning to menu");
                        foundMatch = true;
                        break;
                    }
                }

                // Playing the series
                for (Series series : seriesList) {
                    if (series.getTitle().equalsIgnoreCase(seleact) && series.matchesGenre(genreName)) {
                        series.play(ui);
                        ui.displayMsg("Returning to menu");
                        foundMatch = true;
                        break;
                    }
                }

                // Stops if a title was found, otherwise tells the user to try again
                if (foundMatch) break;
                else ui.displayMsg("No match found for that title under genre: " + genreName + ", try again.");
            }
            break;
        }
    }

    // This method will show all the movies
    public void listAllMovies() {
        Movie.listAllMoviesFromFile(io, movieFilePath, ui);
    }

    // This method will show all the series
    public void listAllSeries() {
        Series.listAllSeriesFromFile(io, seriesFilePath, ui);
    }

    // This method allows the user to save a movie or a series to their saved list
    public void savedMediaList() {
        Media.saveMedia(ui, movieList, seriesList, savedList);
        saveSavedMediaToFile();
    }

    // Loads all movies from the CSV file and stores them in the movieList
    public void loadMoviesFromFile() {
        Movie.loadMoviesFromFile(io, movieFilePath, movieList, ui);
    }

    // Method to see saved list
    public void listOverSavedMedia() {
        if (savedList.isEmpty()) {
            ui.displayMsg("You have no saved media yet!");
            return;
        }

        ui.displayMsg("Here is your saved media:");
        for (Media savedL : savedList) {
            savedL.displayInfo();
        }
    }

    // This method loads all series from the CSV file and stores them in the seriesList
    public void loadSeriesFromFile() {
        Series.loadSeriesFromFile(io, seriesFilePath, seriesList, ui);
    }

    // Method to save all saved media (movies or series) to the savedMedia.csv file
    public void saveSavedMediaToFile() {

        // Create a list to hold each line of text that will go into the CSV
        ArrayList<String> lines = new ArrayList<>();

        // Goes through every media item that the user has saved
        for (Media media : savedList) {

            // The media object returns its own type (Movie or Series)
            String type = media.getType();

            // Creates one single line of text for this media
            String line = currentUser.getMail() + ";" +
                    type + ";" +
                    media.getTitle() + ";" +
                    media.getReleaseYear() + ";" +
                    media.getRating();

            // Adds that line to our list
            lines.add(line);
        }

        // This will save all lines into the CSV file
        io.saveData(lines, savedMediaFilePath, "userEmail;mediaType;title;year;rating");

        // This will just give a small feedback to confirm that the method worked
        ui.displayMsg("Saved " + savedList.size() + " media items to savedMedia.csv!");
    }

    // This method loads all saved media from the CSV file and stores them in the savedList
    public void loadSavedMediaFromFile() {

        // This will read all lines from the saved media file
        ArrayList<String> lines = io.readData(savedMediaFilePath);

        // This goes through each line in the file
        for (String line : lines) {

            // This will split the line into parts using ";" since our CSV uses that as separator
            String[] parts = line.split(";");

            // This makes sure the line has enough data to create a saved media (5 parts minimum)
            if (parts.length < 5) continue;

            // This checks if the media belongs to the logged-in user
            String email = parts[0].trim();
            if (!email.trim().equalsIgnoreCase(currentUser.getMail().trim())) continue;

            // This reads the media type from the second column
            String type = parts[1].trim();

            // This reads the title from the third column
            String title = parts[2].trim();

            // This reads the release year from the fourth column
            int year = 0;
            try {
                year = Integer.parseInt(parts[3].trim());
            } catch (Exception e) {
                continue;
            }

            // This reads the rating from the fifth column
            double rating = 0.0;
            try {
                rating = Double.parseDouble(parts[4].trim().replace(",", "."));
            } catch (Exception e) {
                // Keep rating as 0.0 if parsing fails
            }

            // Creates a Movie or Series object based on type
            if (type.equalsIgnoreCase("Movie")) {
                Movie movie = new Movie(title, new ArrayList<>(), year, rating, this.ui, 0);
                savedList.add(movie);
            } else if (type.equalsIgnoreCase("Series")) {
                Series series = new Series(title, new ArrayList<>(), year, rating, 0, 0, 0);
                savedList.add(series);
            }
        }

        // This is just feedback to make sure it worked
        ui.displayMsg("Loaded " + savedList.size() + " saved media for " + currentUser.getName() + "!");
    }

    // This method saves all watched movies/series to a CSV file
    public void saveSeenMediaToFile() {
        ArrayList<String> lines = new ArrayList<>();

        for (Media media : seenList) {
            String type = (media instanceof Movie) ? "Movie" : "Series";
            String line = currentUser.getName() + ";" +
                    type + ";" +
                    media.getTitle() + ";" +
                    media.getReleaseYear() + ";" +
                    media.getRating();
            lines.add(line);
        }

        io.saveData(lines, seenMediaFilePath, "userName;mediaType;title;year;rating");
        ui.displayMsg("Saved " + seenList.size() + " seen media to seenMedia.csv!");
    }

    // This method loads all watched movies/series from the CSV file
    public void loadSeenMediaFromFile() {
        seenList.clear();
        ArrayList<String> lines = io.readData(seenMediaFilePath);

        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length < 5) continue;

            String name = parts[0].trim();
            if (!name.equalsIgnoreCase(currentUser.getName())) continue;

            String type = parts[1].trim();
            String title = parts[2].trim();

            int year = 0;
            try {
                year = Integer.parseInt(parts[3].trim());
            } catch (Exception e) {
                continue;
            }

            double rating = 0.0;
            try {
                rating = Double.parseDouble(parts[4].trim().replace(",", "."));
            } catch (Exception e) {
                // rating stays 0.0 if parsing fails
            }

            if (type.equalsIgnoreCase("Movie")) {
                seenList.add(new Movie(title, new ArrayList<>(), year, rating, ui, 0));
            } else if (type.equalsIgnoreCase("Series")) {
                seenList.add(new Series(title, new ArrayList<>(), year, rating, 0, 0, 0));
            }
        }

        ui.displayMsg("Loaded " + seenList.size() + " seen media for " + currentUser.getName() + "!");
    }
}





