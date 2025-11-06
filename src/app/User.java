package app;

import java.util.ArrayList;

public class User{
    private String name;
    private String mail;
    private String code;
    private ArrayList<Medie> seenList;
    private ArrayList<Medie> favoriteList;
    private ArrayList<Medie> continueList;


    public User(String name, String mail, String code) {
        this.name = name;
        this.mail = mail;
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getCode() {
        return code;
    }

    public void addToList(String listName, Medie media){
        // Adds a Media to the selected list (seenList, favoriteList, or continueList)
    }

    public void removeFromList(String listName, Medie media){
        // Removes a Media from the selected list (seenList, favoriteList, or continueList)
    }

    public void viewList(String listName){
        // Displays all Media in the selected list (seenList, favoriteList, or continueList)
    }


}
