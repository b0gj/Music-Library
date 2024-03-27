package Model.Full;

import Model.Album;

public class AlbumFull extends Album {
    private String artist;
    private String genre;
    private int songsCount;

    public AlbumFull(String title, String artist, String genre, int songsCount, int releaseYear) {
        super(title,releaseYear);
        this.artist = artist;
        this.genre = genre;
        this.songsCount = songsCount;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getSongsCount() {
        return songsCount;
    }

    public void setSongsCount(int songsCount) {
        this.songsCount = songsCount;
    }
}
