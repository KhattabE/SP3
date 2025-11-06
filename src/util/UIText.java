package util;

import java.util.Scanner;

public class UIText {

    Scanner scanner = new Scanner(System.in);

    //call displayMsg() to display af message
    public void displayMsg(String msg){
        System.out.println(msg);
    }

    public int promptNumeric(String msg){
        displayMsg(msg);
        String input = scanner.nextLine();
        int numInput = Integer.parseInt(input);

        return numInput;
    }


    public String promptText(String msg){
        displayMsg(msg);
        String input = scanner.nextLine();

        return input;
    }


    public boolean promptBinary(String msg){
        displayMsg(msg);
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("Y")){
            return true;
        }
        else if(input.equalsIgnoreCase("N")){
            return false;
        }
        else{
            return promptBinary(msg);
        }
    }


}
