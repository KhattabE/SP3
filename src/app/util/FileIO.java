package app.util;

import app.User;

import java.io.*;
import java.util.ArrayList;


public class FileIO {

    public static void saveUsers(String filepath, ArrayList<User> users){

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

        }catch (IOException ioe){
            System.out.println("Could not write to file!");
        }

    }








}
