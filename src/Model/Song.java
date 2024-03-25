package Model;

public class Song {
    private int ID;
    private String Title;
    private int AlbumID;

    public Song(int id, String title, int albumId) {
        this.ID = id;
        Title = title;
        AlbumID = albumId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(int albumID) {
        AlbumID = albumID;
    }
}
