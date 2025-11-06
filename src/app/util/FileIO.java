package app.util;

import app.User;

import java.io.*;
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








}
