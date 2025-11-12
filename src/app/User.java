package app;

import app.util.UIText;

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

    // A valid mail method so it has to follow critirias to process
    public static boolean isValidMail(String mail) {
        if (mail == null) return false;

        return (mail.contains("@") && mail.contains(".") && mail.indexOf("@") < mail.lastIndexOf(".") &&
                (mail.contains("live") || mail.contains("gmail") || mail.contains("outlook") || mail.contains("hotmail")) &&
                !((mail.contains("gmail") && mail.indexOf("gmail") > mail.lastIndexOf(".")) ||
                        (mail.contains("live") && mail.indexOf("live") > mail.lastIndexOf(".")) ||
                        (mail.contains("outlook") && mail.indexOf("outlook") > mail.lastIndexOf(".")) ||
                        (mail.contains("hotmail") && mail.indexOf("hotmail") > mail.lastIndexOf("."))));
    }

    // Lets a user log in with his/her email and password
    public static User login(UIText ui, ArrayList<User> users) {
        while (true) {
            String mail = ui.promptText("Email: ");

            while (!isValidMail(mail)) {
                mail = ui.promptText("Please enter a valid email!: ");
            }

            String code = ui.promptText("Password: ");

            // Finds the user with that email which is entered
            User u = null;
            for (User existing : users) {
                if (existing.getMail().equalsIgnoreCase(mail)) {
                    u = existing;
                    break;
                }
            }

            // Checks if the user exists and password matches
            if (u != null && u.getCode().equals(code)) {
                ui.displayMsg("Successfully logged in as " + u.getName());
                return u; // return user to MainMenu
            }

            ui.displayMsg("Wrong email or password");

            int choice = ui.promptNumeric("Type 1 to try again, or type 0 to exit to main menu");

            if (choice == 0) {
                ui.displayMsg("Exiting to main menu");
                return null;
            } else if (choice != 1) {
                ui.displayMsg("Invalid input");
            }
        }
    }


    // Creates a new user account
    public static User createAccount(UIText ui, ArrayList<User> users) {
        String name = ui.promptText("Enter a name: ");
        String mail = ui.promptText("Enter an email: ");

        while (!isValidMail(mail)) {
            mail = ui.promptText("Please enter a valid email!: ");
        }

        String code = ui.promptText("Enter a password (at least 4 characters): ");

        //A while loop to make sure the users code is at least 4 chars long
        while (code.length() < 4) {
            code = ui.promptText("Try again! the password must be at least 4 characters! ");
        }

        // Just to show what is being created
        ui.displayMsg("Creating account for: " + name + " (" + mail + ") ");

        // Checks if the mail already exists
        for (User existing : users) {
            if (existing.getMail().equalsIgnoreCase(mail)) {
                ui.displayMsg("Email already exists, please try another email");
                return null; // stops so there won't be created 2 of the same ac's
            }
        }

        // Creates a new user and adds to the list
        User u = new User(name, mail, code);
        users.add(u);

        // Logss the user in
        ui.displayMsg("Account has been created. You are now logged in as " + name + ".");

        return u; // Return user so MainMenu can set currentUser
    }


    // Helper method to check if a user's email already exists
    public static User findUserMail(String mail, ArrayList<User> users) {

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