package app.util;

import app.GenreManager;
import app.User;


import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class FileIO {

    public void saveUsers(String filepath, ArrayList<User> users){

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            writer.write("name;mail;code\n");
            for (User user : users) {
                writer.write(user.getName() + ";" + user.getMail() + ";" + user.getCode() + "\n");
            }

            writer.close();

        }catch (IOException ioe){
            System.out.println("Could not write to file!");
        }

    }



    public ArrayList<User> loadUsers(String filePath){

        String line;
        ArrayList<User> users = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while((line = reader.readLine()) != null){

                String[] row = line.split(";");

                if (row.length == 3) {
                    users.add(new User(row[0], row[1], row[2]));
                }
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File could not be located!");
        } catch (IOException e) {
            System.out.println("An error has happened!");
        }

        return users;

    }


    public ArrayList<Medie> loadMovies(String filePath, GenreManager gm){



    }










}
