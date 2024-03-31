package Model;

public class Song {
    private int ID;
    private String title;
    private int albumID;

    public Song(int id, String title, int albumId) {
        this.ID = id;
        this.title = title;
        albumID = albumId;
    }

    public Song(int ID, String title) {
        this.ID = ID;
        this.title = title;
    }

    public Song(String title, int albumID) {
        this.title = title;
        this.albumID = albumID;
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

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }
}
