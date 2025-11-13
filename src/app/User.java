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




}