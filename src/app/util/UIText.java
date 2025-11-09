package app.util;

import java.util.Scanner;

public class UIText {

    Scanner scanner = new Scanner(System.in);

    //call displayMsg() to display af message
    public void displayMsg(String msg){
        System.out.println(msg);
    }

    public int promptNumeric(String msg){
        while(true){
            displayMsg(msg);
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e){
                displayMsg("Invalid number, you need to try again!");
            }
        }
    }

    public double promptDouble(String msg){
        while(true){
            displayMsg(msg);
            try {
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e){
                displayMsg("Invalid number, you need to try again!");
            }
        }
    }




    public String promptText(String msg){
        displayMsg(msg);
        String input = scanner.nextLine();

        while (input.isEmpty()){
            System.out.println("You must enter something!");
            input = scanner.nextLine();
        }

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

        .
    }


}
