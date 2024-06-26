package Model;

public class Album {
    private int ID;
    private String title;
    private int artistID;
    private int releaseYear;
    private int genreID;

    public Album(int ID, String title, int artistID, int releaseYear, int genreID) {
        this.ID = ID;
        this.title = title;
        this.artistID = artistID;
        this.releaseYear = releaseYear;
        this.genreID = genreID;
    }

    public Album(int ID, String title, int releaseYear) {
        this.ID = ID;
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public Album(String title, int artistID, int releaseYear, int genreID) {
        this.title = title;
        this.artistID = artistID;
        this.releaseYear = releaseYear;
        this.genreID = genreID;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }
}
