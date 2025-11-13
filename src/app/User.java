package app;

import app.util.FileIO;
import app.util.UIText;

import java.util.ArrayList;

public class User {

    // Basic user information
    private String name;
    private String mail;
    private String code;

    // Lists that store the user's media (not used yet, but ready for later features)
    private ArrayList<Media> seenList;
    private ArrayList<Media> favoriteList;
    private ArrayList<Media> continueList;

    // Constructor to create a new user with name, email and password
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

    // Checks if an email is valid based on some simple rules
    public static boolean isValidMail(String mail) {
        if (mail == null) return false;

        return (mail.contains("@") && mail.contains(".") && mail.indexOf("@") < mail.lastIndexOf(".") &&
                (mail.contains("live") || mail.contains("gmail") || mail.contains("outlook") || mail.contains("hotmail")) &&
                !((mail.contains("gmail") && mail.indexOf("gmail") > mail.lastIndexOf(".")) ||
                        (mail.contains("live") && mail.indexOf("live") > mail.lastIndexOf(".")) ||
                        (mail.contains("outlook") && mail.indexOf("outlook") > mail.lastIndexOf(".")) ||
                        (mail.contains("hotmail") && mail.indexOf("hotmail") > mail.lastIndexOf("."))));
    }

    // Lets a user log in with their email and password
    public static User login(UIText ui, ArrayList<User> users) {
        while (true) {
            String mail = ui.promptText("Email: ");

            // Validate email format
            while (!isValidMail(mail)) {
                mail = ui.promptText("Please enter a valid email!: ");
            }

            String code = ui.promptText("Password: ");

            // Find the user with the entered email
            User u = null;
            for (User existing : users) {
                if (existing.getMail().equalsIgnoreCase(mail)) {
                    u = existing;
                    break;
                }
            }

            // Check if user exists and password matches
            if (u != null && u.getCode().equals(code)) {
                ui.displayMsg("Successfully logged in as " + u.getName());
                return u; // Return the logged in user to MainMenu
            }

            ui.displayMsg("Wrong email or password");

            int choice = ui.promptNumeric("Type 1 to try again, or type 0 to exit to main menu: ");

            if (choice == 0) {
                ui.displayMsg("Exiting to main menu");
                return null;
            } else if (choice != 1) {
                ui.displayMsg("Invalid input");
            }
        }
    }

    // Creates a new user account and returns it
    public static User createAccount(UIText ui, ArrayList<User> users) {
        String name = ui.promptText("Enter a name: ");
        String mail = ui.promptText("Enter an email: ");

        // Validate email
        while (!isValidMail(mail)) {
            mail = ui.promptText("Please enter a valid email!: ");
        }

        String code = ui.promptText("Enter a password (at least 4 characters): ");

        // Make sure the password has at least 4 characters
        while (code.length() < 4) {
            code = ui.promptText("Try again! The password must be at least 4 characters! ");
        }

        // Show what is being created
        ui.displayMsg("Creating account for: " + name + " (" + mail + ") ");

        // Check if the email already exists
        for (User existing : users) {
            if (existing.getMail().equalsIgnoreCase(mail)) {
                ui.displayMsg("Email already exists, please try another email");
                return null; // Stop so we don't create a duplicate account
            }
        }

        // Create a new user and add it to the list
        User u = new User(name, mail, code);
        users.add(u);

        // Confirm to the user
        ui.displayMsg("Account has been created. You are now logged in as " + name + ".");

        // Return the created user so MainMenu can set currentUser
        return u;
    }

    // Loads all users from a CSV file into the provided list
    public static void loadUsersFromFile(FileIO io,
                                         String userFilePath,
                                         ArrayList<User> users,
                                         UIText ui) {

        users.clear(); // Remove any users currently in memory

        // Read all lines from the file
        ArrayList<String> lines = io.readData(userFilePath);

        // Each line is split into parts: name;mail;code
        for (String line : lines) {
            String[] p = line.split(";");
            if (p.length >= 3) { // Make sure we have all 3 fields
                users.add(new User(p[0], p[1], p[2]));
            }
        }

        // Feedback to the user
        if (users.isEmpty()) {
            ui.displayMsg("No users found in file.");
        } else {
            ui.displayMsg("Users loaded from file:");
            for (User u : users) {
                ui.displayMsg(" - " + u.getName() + " (" + u.getMail() + ")");
            }
        }
    }

    // Saves all users in memory to the users CSV file
    public static void saveUsersToFile(FileIO io,
                                       String userFilePath,
                                       ArrayList<User> users) {

        ArrayList<String> lines = new ArrayList<>();

        // Convert each user object into a CSV line: name;mail;code
        for (User u : users) {
            lines.add(u.getName() + ";" + u.getMail() + ";" + u.getCode());
        }

        // Use FileIO to write the data to the file
        io.saveData(lines, userFilePath, "name;mail;code");
    }
}