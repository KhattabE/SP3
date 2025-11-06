package app;

public class Genre {
    private String genreName;
    private String genreDescription;

    public Genre(String genreName, String genreDescription) {
        this.genreName = genreName;
        this.genreDescription = genreDescription;
    }

    public String getGenreName() {
        return genreName;
    }

    public String getGenreDescription() {
        return genreDescription;
    }

    @Override
    public String toString(){
        return genreName + " (" + genreDescription + ")";
    }
}
