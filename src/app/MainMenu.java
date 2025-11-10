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

    // These are lists to hold all of the movies, series, and users.
    private ArrayList<Movie> movieList;
    private ArrayList<Series> seriesList;
    private ArrayList<User> users;

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
            ui.displayMsg("5: List over seen movies/series");
            ui.displayMsg("6: List over saved movies/series");
            ui.displayMsg("7: Back to user menu");
            ui.displayMsg("0: Log-out");

            // Ask the user for a choice
            int choice = ui.promptNumeric("Enter your choice: ");

            switch (choice) {
                case 1 -> {
                    ui.displayMsg("All movies available: ");
                    listAllMovies();
                    String input = ui.promptText("Type 0 to return to menu: ");
                    if (input.equals("0")) continue;

                }

                case 2 -> {
                    ui.displayMsg("All series available: ");
                    listAllSeries();
                    String input = ui.promptText("Type 0 to return to menu: ");
                    if (input.equals("0")) continue;

                }

                case 3 -> {
                    searchAfterMedia();

                    String input = ui.promptText("Type 0 to return to menu: ");
                    if (input.equals("0")) continue;

                }

                case 4 ->{
                    searchAfterSameGenreMedia();

                    String input = ui.promptText("Type 0 to return to menu: ");
                    if (input.equals("0")) continue;


                }

                case 5 ->{

                }

                case 6 ->{

                }

                case 7-> {
                    ui.displayMsg("Returning to user menu");
                    return;
                }

                case 0 -> {
                    ui.displayMsg("Logging out...");
                    currentUser = null; // Set to null which means that no user is logged in anymore
                }

                default -> ui.displayMsg("Invalid choice, please try again.");
            }

            // If the user logs out then go back to main menu
            if (currentUser == null) {
                break;
            }
        }
    }

    //This method is for searching for movie
    public void searchAfterMedia(){
        String mediaName = ui.promptText("Enter the name of the movie/series you are searching for: ");

        boolean isFound = false;

        //Loops through the movieList arraylist
        for(Movie movie : movieList){
            //Checks if the movie title equals what the user have written
            if(movie.getTitle().equalsIgnoreCase(mediaName)){
                ui.displayMsg("Movie Found!");
                //Shows info about the movie
                movie.displayInfo();
                isFound = true;

            }
        }

        //Loops through the seriesList arraylist
        for(Series series : seriesList){
            //Checks if the series title equals what the user have written
            if(series.getTitle().equalsIgnoreCase(mediaName)){
                ui.displayMsg("Series Found!");
                series.displayInfo();
                isFound = true;
                break;
            }
        }

        if(!isFound){
            ui.displayMsg("No movie or series found with the given name!");
        }



    }


    //Method to find all media under a searched genre
    public void searchAfterSameGenreMedia(){
        String genreName = ui.promptText("Enter the genre, to see all media from that genre: ");

        boolean genreIsFound = false;

        //Searches through all movies
        for(Movie movie : movieList){
            if(movie.matchesGenre(genreName)){
                ui.displayMsg("We have found all movies that matches this genre!");
                movie.displayInfo();
                genreIsFound = true;
            }
        }

        // Search through all series
        for (Series series : seriesList) {
            if (series.matchesGenre(genreName)) {
                ui.displayMsg("We have found all series that match this genre!");
                series.displayInfo();
                genreIsFound = true;
            }
        }

        // If nothing was found
        if (!genreIsFound) {
            ui.displayMsg("No movies or series found with theis genre: " + genreName);
            
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




}









