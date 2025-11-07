package app;

import app.util.UIText;

import java.util.ArrayList;

public class MainMenu {

    private String userFilePath = "users.csv";
    private String movieFilePath = "movies.csv";
    private String seriesFilePath = "series.csv";
    private ArrayList<Movie> movieList;
    private ArrayList<Series> seriesList;
    private ArrayList<User> users;
    private User currentUser;
    UIText ui;


    public MainMenu(String userFilePath, String seriesFilePath, String movieFilePath) {
        this.userFilePath = userFilePath;
        this.seriesFilePath = seriesFilePath;
        this.movieFilePath = movieFilePath;
    }


    public void show(){
        ui.displayMsg("Welcome to the streaming service Amandu-Entertainments!");
        ui.displayMsg("You got the following options you can choose from: ");
        ui.displayMsg("1: Login");
        ui.displayMsg("2: Create an account");
        ui.promptNumeric("Enter your choice (1: Login, 2: Create an account:) ");



    }

    public void login(){
        // Login if the user has an ac
    }

    public void createAccount(){
        // Lets the user create an ac
    }

    public void loadUsersFromFile(){
        // Reads the user from a file
    }

    public void saveUsersToFile(){
        // Saves the user in a file
    }

    public void showUserMenu(){
        // Shows the user Menu
    }







}
