package Model.Full;

import Model.Artist;

public class ArtistFull extends Artist {
    private int albumsCount;
    private int songsCount;

    public ArtistFull(int ID, String name, int albumsCount, int songsCount, int birthYear) {
        super(ID, name, birthYear);
        this.albumsCount = albumsCount;
        this.songsCount = songsCount;
    }

    public int getAlbumsCount() {
        return albumsCount;
    }

    public void setAlbumsCount(int albumsCount) {
        this.albumsCount = albumsCount;
    }

    public int getSongsCount() {
        return songsCount;
    }

    public void setSongsCount(int songsCount) {
        this.songsCount = songsCount;
    }
}
