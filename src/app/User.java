package app;

import java.util.ArrayList;

public class User {
    // user information
    private String name;
    private String mail;
    private String code;

    // Lists that stores the user media
    private ArrayList<Media> seenList;
    private ArrayList<Media> favoriteList;
    private ArrayList<Media> continueList;


    // Constructor
    public User(String name, String mail, String code) {
        this.name = name;
        this.mail = mail;
        this.code = code;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getCode() {
        return code;
    }

    // Adds a media item to one of the lists
    public void addToList(String listName, Media media) {
        if (listName.equalsIgnoreCase("seen")) {
            seenList.add(media);
        } else if (listName.equalsIgnoreCase("favorite")) {
            favoriteList.add(media);
        } else if (listName.equalsIgnoreCase("continue")) {
            continueList.add(media);
        }
    }

    // Removes a media item from one of the lists
    public void removeFromList(String listName, Media media) {
        if (listName.equalsIgnoreCase("seen")) {
            seenList.remove(media);
        } else if (listName.equalsIgnoreCase("favorite")) {
            favoriteList.remove(media);
        } else if (listName.equalsIgnoreCase("continue")) {
            continueList.remove(media);
        }
    }

    // Prints out all the media titles from a specific list
    public void viewList(String listName) {
        ArrayList<Media> list = null;

        // Decide which list to show based on the name
        if (listName.equalsIgnoreCase("seen")) {
            list = seenList;
        } else if (listName.equalsIgnoreCase("favorite")) {
            list = favoriteList;
        } else if (listName.equalsIgnoreCase("continue")) {
            list = continueList;
        }

        // If the list exists then show all titles in it
        if (list != null) {
            for (Media media : list) {
                System.out.println(media.getTitle());
            }
        }
    }
}