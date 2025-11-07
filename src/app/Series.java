package app;

import java.util.ArrayList;

public class Series extends Media  {



    @Override
    public void medieIsPlaying(){
        ui.displayMsg("The series is now playing...");
    }

    @Override
    public void medieIsPaused(){
        ui.displayMsg("The series has been paused...");
    }

    @Override
    public void medieIsEnded(){
        ui.displayMsg("The series has been ended...");
    }






}
