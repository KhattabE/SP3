package app;

import app.util.UIText;

import java.util.ArrayList;

public class MainMenu {

    // File paths for storing users, movies, and also series
    private String userFilePath = "users.csv";
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


    // Lets a user log in with his/her email and password
    public void login() {
        String mail = ui.promptText("Email: ");
        String code = ui.promptText("Password: ");

        // Finds the user with that email which is entered
        User u = findUserMail(mail);

        // Checks if the user does exist and that the password matches
        if (u != null && u.getCode().equals(code)) {
            currentUser = u;
            ui.displayMsg("Successfully logged in as " + u.getName());
        } else {
            ui.displayMsg("Wrong email or password");
        }
    }


    // Creates a new user account
    public void createAccount() {
        String name = ui.promptText("Enter a name: ");


        String mail = ui.promptText("Enter an email: ");


        while(!mail.contains("@") || !mail.contains(".") ||  mail.indexOf("@") > mail.lastIndexOf(".") || !mail.contains("live") && !mail.contains("gmail") && !mail.contains("outlook") && !mail.contains("hotmail")  ||
                ((mail.contains("gmail") && mail.indexOf("gmail") > mail.lastIndexOf(".")) ||
                                (mail.contains("live") && mail.indexOf("live") > mail.lastIndexOf(".")) ||
                                (mail.contains("outlook") && mail.indexOf("outlook") > mail.lastIndexOf(".")) ||
                                (mail.contains("hotmail") && mail.indexOf("hotmail") > mail.lastIndexOf(".")))){

            mail = ui.promptText("Please enter a valid email!: ");


        }




        String code = ui.promptText("Enter a password ");

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
    }

    //  will later load users from a file when fileIo is done
    public void loadUsersFromFile() {
        // loads the users from a file
    }



    //  will later save users to a file when fileIo is done
    public void saveUsersToFile() {
        // Saves the users to a file
    }




    // The menu shown after a user has logged in
    public void showUserMenu() {

        while (true) {
            ui.displayMsg("\nWelcome, " + currentUser.getName());
            ui.displayMsg("\n1:  ");
            ui.displayMsg("\n2:  ");
            ui.displayMsg("\n3:  ");
            ui.displayMsg("\n0: Logout");


            // Ask the user for a choice
            int choice = ui.promptNumeric("Enter your choice: ");

            switch (choice) {
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