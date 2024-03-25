package Model;

public class Album {
    private int ID;
    private String title;
    private int ArtistID;
    private int releaseYear;
    private String genre;

    public Album(int id, String title, int artistId, int releaseYear, String genre) {
        this.ID = id;
        this.title = title;
        ArtistID = artistId;
        this.releaseYear = releaseYear;
        this.genre = genre;
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
        return ArtistID;
    }

    public void setArtistID(int artistID) {
        ArtistID = artistID;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
