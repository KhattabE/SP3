package app.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

    // This saves the data from a list to a file and also adds a header at the top
    public void saveData(ArrayList<String> list, String path, String header) {
        try {
            FileWriter writer = new FileWriter(path); // This opens the file for writing
            writer.write(header + "\n"); // This writes the header line first

            // This writes each string from the list in a new line
            for (String s : list) {
                writer.write(s + "\n");
            }

            writer.close(); // This closes the file when done
        } catch (IOException e) {
            System.out.println("Problem while writing file: " + e.getMessage());
        }
    }

    // This reads data from a file and then returns it as a list it also skips the headerLine
    public ArrayList<String> readData(String path) {
        ArrayList<String> data = new ArrayList<>();
        File file = new File(path);

        try {
            Scanner scan = new Scanner(file); // This opens the file for reading

            if (scan.hasNextLine()) {
                scan.nextLine(); // This skips the first line which is the header
            }

            // This reads all remaining lines and add them to the list
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                data.add(line);
            }

            scan.close(); // This closes the file when done
        } catch (Exception e) {
            System.out.println("Could not read file: " + e.getMessage());
        }

        return data; // This returns all lines as a list
    }

    // This reads a specific number of lines from a file
    public String[] readData(String path, int length) {
        String[] data = new String[length];
        File file = new File(path);

        try {
            Scanner scan = new Scanner(file); // This opens the file for reading

            if (scan.hasNextLine()) {
                scan.nextLine(); // This skips the first line
            }

            // This reada to the length of the lines and store them in the array
            for (int i = 0; i < length && scan.hasNextLine(); i++) {
                data[i] = scan.nextLine();
            }

            scan.close(); // This closes the file when done
        } catch (Exception e) {
            System.out.println("Could not read file: " + e.getMessage());
        }

        return data; // Finally this returns all lines which are read as an array
    }
}