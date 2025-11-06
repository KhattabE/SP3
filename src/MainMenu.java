import java.util.ArrayList;

public class MainMenu {

    private String userFilePath;
    private String movieFilePath;
    private String seriesFilePath;
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
        // Display the welcome screen
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
