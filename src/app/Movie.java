package app;

import java.util.ArrayList;

public class Movie extends Media implements MediePlay, MediePause, MedieEnd  {


    @Override
    public void medieIsPlaying(){
        ui.displayMsg("The movie is now playing...");
    }

    @Override
    public void medieIsPaused(){
        ui.displayMsg("The movie has been paused...");
    }

    @Override
    public void medieIsEnded(){
        ui.displayMsg("The movie has been ended...");
    }






}
