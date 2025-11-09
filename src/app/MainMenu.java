package app;

import app.util.UIText;
import app.util.FileIO;

import java.util.ArrayList;

public class MainMenu {

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
        loadUsersFromFile(); // load users from CSV at start

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
            ui.displayMsg("1: Search for a movie or a series");
            ui.displayMsg("2: List all movies");
            ui.displayMsg("3: List all series");
            ui.displayMsg("4: Back to user menu");
            ui.displayMsg("0: Log-out");

            // Ask the user for a choice
            int choice = ui.promptNumeric("Enter your choice: ");

            switch (choice) {
                case 1 -> ui.displayMsg("Search for a movie or a series: ");

                case 2 -> {
                    listAllMovies();
                    ui.displayMsg("All movies available: ");
                    String input = ui.promptText("Type 0 to return to menu: ");
                    if (input.equals("0")) continue;
                }

                case 3 -> {
                    listAllSeries();
                    ui.displayMsg("All series available: ");
                    String input = ui.promptText("Type 0 to return to menu: ");
                    if (input.equals("0")) continue;
                }

                case 4 -> {
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

    public void listAllMovies() {
        for (Movie m : movieList) {
            m.displayInfo();
        }
    }

    public void listAllSeries() {
        for (Series s : seriesList) {
            s.displayInfo();
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
}