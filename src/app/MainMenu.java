package app;

import app.util.UIText;
import app.util.FileIO;

import java.util.ArrayList;

public class MainMenu {


    //
    private Media media;

    // File paths for storing users, movies, and also series
    private String userFilePath = "data/users.csv";
    private String movieFilePath = "movies.csv";
    private String seriesFilePath = "series.csv";
    private String savedMediaFilePath = "data/savedMedia.csv";


    // These are lists to hold all of the movies, series, and users.
    private ArrayList<Movie> movieList;
    private ArrayList<Series> seriesList;
    private ArrayList<User> users;
    private ArrayList<Media> savedList;
    private ArrayList<Media> seenList;

    // this keeps track of the user currently logged in
    private User currentUser;

    // Used to handle input
    private UIText ui;

    // File I/O helper
    private final FileIO io = new FileIO();

    // Constructor
    public MainMenu(String userFilePath, String movieFilePath, String seriesFilePath) {
        this.userFilePath = userFilePath;
        this.movieFilePath = movieFilePath;
        this.seriesFilePath = seriesFilePath;

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
        loadUsersFromFile(); // load users from csv at start
        loadMoviesFromFile(); //Loads movies from csv at start
        loadSeriesFromFile(); //Loads series from csv at start


        boolean isRunning = true;
        while (isRunning) {
            ui.displayMsg("\nWelcome to the streaming service AEMK Entertainments!");
            ui.displayMsg("You got the following options you can choose from: ");
            ui.displayMsg("1: Login");
            ui.displayMsg("2: Create an account");
            ui.displayMsg("0: Exit");

            int choice = ui.promptNumeric("Enter your choice: ");

            // a Switch case to handle the users choice
            switch (choice) {
                case 1 -> login();            // if 1 then Login to an existing account
                case 2 -> createAccount();    // if 2 then Create a new user account
                case 0 -> isRunning = false; // if 0 then Exit the program
                default -> ui.displayMsg("Invalid choice, please try again.");
            }

            // If a user is logged in it then opens the user menu
            if (currentUser != null) {
                showUserMenu();
            }
        }
    }

    // A valid mail method so it has to follow critirias to process
    public boolean validMail(String mail) {
        if (mail == null) return false;

        return (mail.contains("@") && mail.contains(".") && mail.indexOf("@") < mail.lastIndexOf(".") &&
                (mail.contains("live") || mail.contains("gmail") || mail.contains("outlook") || mail.contains("hotmail")) &&
                !((mail.contains("gmail") && mail.indexOf("gmail") > mail.lastIndexOf(".")) ||
                        (mail.contains("live") && mail.indexOf("live") > mail.lastIndexOf(".")) ||
                        (mail.contains("outlook") && mail.indexOf("outlook") > mail.lastIndexOf(".")) ||
                        (mail.contains("hotmail") && mail.indexOf("hotmail") > mail.lastIndexOf("."))));
    }

    // Lets a user log in with his/her email and password
    public void login() {
        while (true) {
            String mail = ui.promptText("Email: ");

            while (!validMail(mail)) {
                mail = ui.promptText("Please enter a valid email!: ");
            }

            String code = ui.promptText("Password: ");

            // Finds the user with that email which is entered
            User u = findUserMail(mail);

            // Checks if the user does exist and that the password matches
            if (u != null && u.getCode().equals(code)) {
                currentUser = u;
                ui.displayMsg("Successfully logged in as " + u.getName());
                loadSavedMediaFromFile(); //Loads savedMedia csv at start
                break;
            }
            ui.displayMsg("Wrong email or password");

            int choice = ui.promptNumeric("Type 1 to try again, or type 0 to exit to main menu");

            if (choice == 0) {
                ui.displayMsg("Exiting to main menu");
                return;
            } else if (choice != 1) {
                ui.displayMsg("Invalid input");
            }
        }
    }

    // Creates a new user account
    public void createAccount() {
        String name = ui.promptText("Enter a name: ");
        String mail = ui.promptText("Enter an email: ");

        while (!validMail(mail)) {
            mail = ui.promptText("Please enter a valid email!: ");
        }

        String code = ui.promptText("Enter a password (4 chars at least) ");

        //A while loop to make sure the users code is at least 4 chars long
        while (code.length() < 4) {
            code = ui.promptText("Try again! the password must be 4 chars at least! ");
        }

        // Just to show what is being created
        ui.displayMsg("Creating account for: " + name + " (" + mail + ") ");

        // Checks if the mail already exists
        User found = findUserMail(mail);
        if (found != null) {
            ui.displayMsg("Email already exists, please try another email");
            return; // stops so there won't be created 2 of the same ac's
        }

        // Creates a new user and adds to the list
        User u = new User(name, mail, code);
        users.add(u);

        // Logs the user in
        currentUser = u;

        ui.displayMsg("Account has been created. You are now logged in as " + name + ".");

        // Save updated users list to file
        saveUsersToFile();
    }

    // This method loads users from a file using FileIO
    public void loadUsersFromFile() {
        users.clear(); // This remove any users currently in memory

        // This reads all lines from the file
        ArrayList<String> lines = io.readData(userFilePath);

        // for each line is split into parts (name;mail;code)
        for (String line : lines) {
            String[] p = line.split(";");
            if (p.length >= 3) { // This makes sure the line has all 3 parts
                users.add(new User(p[0], p[1], p[2])); // This creates a new User and adds it to the list
            }
        }

        // if theire is no users were found, tell the user else show all loaded users
        if (users.isEmpty()) {
            ui.displayMsg("No users found in file.");
        } else {
            ui.displayMsg("Users loaded from file:");
            for (User u : users) {
                ui.displayMsg(" - " + u.getName() + " (" + u.getMail() + ")");
            }
        }
    }

    // This method saves all users in memory to the users file
    public void saveUsersToFile() {
        ArrayList<String> lines = new ArrayList<>();

        // This converts each user object into a single line of text which is a: (name;mail;code)
        for (User u : users) {
            lines.add(u.getName() + ";" + u.getMail() + ";" + u.getCode());
        }

        // This sends the data to FileIO to actually write it into the file
        io.saveData(lines, userFilePath, "name;mail;code");
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
                    ui.displayMsg("All movies available: ");
                    listAllMovies();
                    String input = ui.promptText("Type 0 to return to menu: ");
                    while (!input.equals("0"))
                        input = ui.promptText("Please type 0 to return to menu: ");

                }

                case 2 -> {
                    ui.displayMsg("All series available: ");
                    listAllSeries();
                    String input = ui.promptText("Type 0 to return to menu: ");
                    while (!input.equals("0"))
                        input = ui.promptText("Please type 0 to return to menu: ");
                }

                case 3 -> {
                    searchAfterMedia();

                }

                case 4 -> {
                    searchAfterSameGenreMedia();
                }

                case 5 -> {
                    savedMediaList();

                    String input = ui.promptText("Type 0 to return to menu: ");
                    while (!input.equals("0"))
                        input = ui.promptText("Please type 0 to return to menu: ");
                }

                case 6 -> {

                    listOverSavedMedia();

                    String input = ui.promptText("Type 0 to return to menu: ");
                    while (!input.equals("0"))
                        input = ui.promptText("Please type 0 to return to menu: ");

                }
                case 7 -> {

                }

                case 8 -> {
                    ui.displayMsg("Logging out and returning to user menu");
                    return;
                }

                case 0 -> {
                    ui.displayMsg("Closing AEMK Entertainments!");
                    currentUser = null; // Set to null which means that no user is logged in anymore
                    System.exit(0); // Closes program
                }

                default -> ui.displayMsg("Invalid choice, please try again.");
            }
        }
    }

    //This method is for searching for movie
    public void searchAfterMedia() {
        String mediaName = ui.promptText("Enter the name of the movie/series you are searching for: ");
        while (true) {
            boolean isFound = false;

            //Loops through the movieList arraylist
            for (Movie movie : movieList) {
                //Checks if the movie title equals what the user have written
                if (movie.getTitle().equalsIgnoreCase(mediaName)) {
                    ui.displayMsg("Movie Found!");
                    //Shows info about the movie
                    movie.displayInfo();
                    ui.displayMsg("");

                    String choice = ui.promptText("Type 0 to exit or type 1 to play " + mediaName);
                    if (choice.equals("1")) {
                        ui.displayMsg("Now playing: " + movie.getTitle());
                        ui.displayMsg(movie.getTitle() + " is playing...");
                        ui.displayMsg(movie.getTitle() + " is finished");
                        ui.displayMsg("Returning to menu");
                    }
                    isFound = true;
                    break;
                }
            }

            //Loops through the seriesList arraylist
            for (Series series : seriesList) {
                //Checks if the series title equals what the user have written
                if (series.getTitle().equalsIgnoreCase(mediaName)) {
                    ui.displayMsg("Series Found!");
                    series.displayInfo();
                    ui.displayMsg("");

                    String choice = ui.promptText("Type 0 to exit or type 1 to play " + mediaName);
                    if (choice.equals("1")) {
                        ui.displayMsg("Now playing: " + series.getTitle());
                        ui.displayMsg(series.getTitle() + " is playing...");
                        ui.displayMsg(series.getTitle() + " is finished");
                        ui.displayMsg("Returning to menu");
                    }
                    isFound = true;
                    break;
                }

            }
            if (!isFound) {
                ui.displayMsg("");
                ui.displayMsg("No movie or series found with the given name!");
                String input = ui.promptText("Try again or type 0 to return to menu");


                if (input.equals("0")) {
                    return;
                }
                mediaName = input;
            } else {
                break;
            }

        }
    }

    // thid method is to find all media under a searched genre
    public void searchAfterSameGenreMedia() {
        String genreName = ui.promptText("Enter the genre, to see all media from that genre: ");

        while (true) {
            boolean genreIsFound = false;

            ui.displayMsg("");

            // showing all movies

            for (Movie movie : movieList) {
                if (movie.matchesGenre(genreName)) {
                    if (!genreIsFound) {
                        ui.displayMsg("**** All movies and series in " + genreName + " *****" );
                        ui.displayMsg("");
                        genreIsFound = true;
                    }
                    movie.displayInfo();
                    ui.displayMsg("");
                }
            }

            // shows all series
            for (Series series : seriesList) {
                if (series.matchesGenre(genreName)) {
                    if (!genreIsFound) {
                        ui.displayMsg("**** All series and movies in " + genreName + " *****" );
                        ui.displayMsg("");
                        genreIsFound = true;
                    }
                    series.displayInfo();
                    ui.displayMsg("");
                }
            }

            // if no series or movie is found
            if (!genreIsFound) {
                ui.displayMsg("No movies or series found with the genre: " + genreName);
                String input = ui.promptText("Try again or type 0 to return to menu");
                if (input.equals("0")) return;
                genreName = input;
                continue;
            }

            // asks user which one to play
            while (true) {
                String seleact = ui.promptText("Type the exact title you want to play or 0 to return to menu");
                if (seleact.equals("0")) return;

                boolean foundMatch = false;

                // Playing the movie
                for (Movie movie : movieList) {
                    if (movie.getTitle().equalsIgnoreCase(seleact) && movie.matchesGenre(genreName)) {
                        ui.displayMsg("Now playing: " + movie.getTitle());
                        ui.displayMsg(movie.getTitle() + " is playing...");
                        ui.displayMsg(movie.getTitle() + " is finished");
                        ui.displayMsg("Returning to menu");
                        foundMatch = true;
                        break;
                    }
                }

                // playing the series
                for (Series series : seriesList) {
                    if (series.getTitle().equalsIgnoreCase(seleact) && series.matchesGenre(genreName)) {
                        ui.displayMsg("Now playing: " + series.getTitle());
                        ui.displayMsg(series.getTitle() + " is playing...");
                        ui.displayMsg(series.getTitle() + " is finished");
                        ui.displayMsg("Returning to menu");
                        foundMatch = true;
                        break;
                    }
                }

                if (foundMatch) break; // stopper hvis der blev fundet en titel
                else ui.displayMsg("No match found for that title under genre: " + genreName + ", try again.");
            }
            break;
        }
    }





    //This method will show all the Movies
    public void listAllMovies() {

        // Read all lines from the Movies CSV file (FileIO automatically skips the header)
        ArrayList<String> lines = io.readData(movieFilePath);

        // If the file is empty or could not be read, tell the user and stop
        if (lines.isEmpty()) {
            ui.displayMsg("No Movies found.");
            return;
        }

        // If we got some lines, show them to the user
        ui.displayMsg("Movies from file:");
        for (String line : lines) {
            // Print each Movies line exactly as it appears in the CSV file
            ui.displayMsg(" - " + line);
        }
    }

    //This method will show all the series
    public void listAllSeries() {

        // Read all lines from the series CSV file (FileIO automatically skips the header)
        ArrayList<String> lines = io.readData(seriesFilePath);

        // If the file is empty or could not be read, tell the user and stop
        if (lines.isEmpty()) {
            ui.displayMsg("No series found.");
            return;
        }

        // If we got some lines, show them to the user
        ui.displayMsg("Series from file:");
        for (String line : lines) {
            // Print each series line exactly as it appears in the CSV file
            ui.displayMsg(" - " + line);
        }
    }

    //Method to save media to a saved list
    public void savedMediaList() {

        ui.displayMsg("Would you like to save a movie or a series?");
        ui.displayMsg("1: Save a movie");
        ui.displayMsg("2: Save a series");
        int choice = ui.promptNumeric("Enter your choice: ");

        boolean isFound = false;

        //an if statment to check which choice the user makes
        if (choice == 1) {
            //This is where the user will write the movie name
            String name = ui.promptText("Enter the exact name of the movie you want to save: ");

            for (Movie movie : movieList) {

                //If statment to check if the movie exists or not
                if (name.equalsIgnoreCase(movie.getTitle())) {
                    ui.displayMsg("Movie found!");
                    savedList.add(movie);
                    ui.displayMsg("Movie saved successfully!");
                    isFound = true;
                    break;
                }

            }


        } else if (choice == 2) {

            //This is where the user will write the series name
            String name = ui.promptText("Enter the exact name of the series you want to save: ");

            for (Series series : seriesList) {


                //If statment to check if the movie exists or not
                if (name.equalsIgnoreCase(series.getTitle())) {
                    ui.displayMsg("Series found!");
                    savedList.add(series);
                    ui.displayMsg("Series saved successfully!");
                    isFound = true;
                    break;
                }

            }


        }

        if (!isFound) {
            ui.displayMsg("No Media exists with that name!");
        }


        // Save to file at the end
        saveSavedMediaToFile();

    }

    //Method to see saved list
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


    // Helper method to check if a user's email already exists
    private User findUserMail(String mail) {

        // Loops through every user in the list
        for (User u : users) {

            // Checks if the user mail matches
            if (u.getMail().equalsIgnoreCase(mail)) {
                return u; // Return this user if found
            }
        }
        // If no user has that mail then return null
        return null;
    }


    // Loads all movies from the csv file and stores them in the movieList
    public void loadMoviesFromFile() {

        // This will read all lines from the movie file
        ArrayList<String> lines = io.readData(movieFilePath);

        // This goes through each line in the file
        for (String line : lines) {

            // This will split the line into parts using ";" since our csv uses that as separator
            String[] parts = line.split(";");

            // This makes sure the line has enough data to create a movie (at least 4 parts)
            if (parts.length < 4) continue;

            // This reads the movie title from the first column
            String title = parts[0].trim();

            // This reads the release year from the second column
            int year = 0;
            try {
                year = Integer.parseInt(parts[1].trim());
            } catch (Exception e) {
                // If something goes wrong this will skip this movie and continue with the next line
                continue;
            }

            // This reads the rating from the last column
            // we sat 0.0 to be the default value, but this is only if parsing fails
            double rating = 0.0;
            try {
                String ratingText = parts[parts.length - 1].trim();

                // This is to replace the comma with a dot
                ratingText = ratingText.replace(",", ".");

                rating = Double.parseDouble(ratingText);
            } catch (Exception e) {
                // If the rating cant be read, this will keep it as 0.0
            }


            // Reads all genres from the remaining columns
            ArrayList<String> genres = new ArrayList<>();
            for (int i = 2; i < parts.length - 1; i++) {  // notice "-1" here
                String genre = parts[i].trim();
                if (!genre.isEmpty()) {
                    genres.add(genre);
                }
            }

            // Creates a Movie object from the csv line
            Movie movie = new Movie(title, genres, year, rating, this.ui, 0);

            // Adds the movie to the list in memory
            movieList.add(movie);
        }

        // This is just fedback to make sure it worked
        ui.displayMsg("Loaded " + movieList.size() + " movies from file!");
    }


    // So this method loads all series from the csv file and stores them in the seriesList
    public void loadSeriesFromFile() {

        // This will read all lines from the series file
        ArrayList<String> lines = io.readData(seriesFilePath);

        // This goes through each line in the file
        for (String line : lines) {

            // This will split the line into parts using ";" since our csv uses that as separator
            String[] parts = line.split(";");

            // This makes sure the line has enough data to create a series (at least 5 parts)
            if (parts.length < 3) continue;

            // This reads the series title from the first column
            String title = parts[0].trim();

            // This reads the release year from the second column
            int year = 0;
            try {
                String yearText = parts[1].trim();

                //takes only the first part
                if (yearText.contains("-")) {
                    yearText = yearText.split("-")[0].trim();
                }

                year = Integer.parseInt(yearText);
            } catch (Exception e) {
                // this is if something goes wrong, this will skip this series and continue with the next line
                continue;
            }


            // This reads all genres from the middle columns
            ArrayList<String> genres = new ArrayList<>();
            for (int i = 2; i < parts.length - 2; i++) { // everything except year, title, rating, and after
                String genre = parts[i].trim();
                if (!genre.isEmpty()) {
                    genres.add(genre);
                }
            }

            // This reads the rating from the second to last column
            double rating = 0.0;
            try {
                String ratingText = parts[parts.length - 2].trim().replace(",", ".");
                rating = Double.parseDouble(ratingText);
            } catch (Exception e) {
                // If rating is not readable, it will keep it as 0.0
            }


            // This reads the last column which has season + episode codes
            String episodeData = parts[parts.length - 1].trim();
            int seasons = 0;
            int episodes = 0;


            // Creates a Series object from the csv line
            Series series = new Series(title, genres, year, rating, seasons, episodes, 0);

            // Here we add the series to the list in memory
            seriesList.add(series);
        }

        // This is just feedback to make sure it worked
        ui.displayMsg("Loaded " + seriesList.size() + " series from file!");
    }


    // Method to save all saved media (movies or series) to the savedMedia.csv file
    public void saveSavedMediaToFile() {

        // Create a list to hold each line of text that will go into the CSV
        ArrayList<String> lines = new ArrayList<>();

        // Goes through every media item that the user has saved
        for (Media media : savedList) {

            // Creates one single line of text for this media
            String line = currentUser.getName() + ";" +
                    "Media" + ";" +
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


    // So this method loads all saved media from the csv file and stores them in the savedList
    public void loadSavedMediaFromFile() {

        // This will read all lines from the saved media file
        ArrayList<String> lines = io.readData(savedMediaFilePath);

        // This goes through each line in the file
        for (String line : lines) {

            // This will split the line into parts using ";" since our csv uses that as separator
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
                // keep rating as 0.0 if parsing fails
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


}









