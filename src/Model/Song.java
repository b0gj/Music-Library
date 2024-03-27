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

    public Song(String title) {
        this.title = title;
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
