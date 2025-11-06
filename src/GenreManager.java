import java.util.ArrayList;

public class GenreManager {

    private ArrayList<Genre> genreList;

    public GenreManager(ArrayList<Genre> genreList) {
        this.genreList = genreList;
    }

    public void addGenre(Genre genre){
        genreList.add(genre);

    }

    public ArrayList<Genre> getAllGenres(){
        return genreList;
    }

    public Genre getGenreByName(String name){
        // Find genre by name
    }

    public void printGenres(){
        // Prints genres
    }

}
