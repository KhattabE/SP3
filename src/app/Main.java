package app;

import app.util.FileIO;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        MainMenu menu = new MainMenu(
                "data/users.csv",
                "data/movies.csv",
                "data/series.csv"
        );

        menu.show(); // Starts the mainMenu
    }
}
