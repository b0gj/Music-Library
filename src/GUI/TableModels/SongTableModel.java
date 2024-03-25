package GUI.TableModels;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import DAOs.AlbumDAO;
import DAOs.ArtistDAO;
import DAOs.GenreDAO;
import Model.Song;

public class SongTableModel extends AbstractTableModel {
    private final List<Song> songs;
    private final String[] columnNames = {"Title", "Artist", "Album", "Genre", "Release Year"};

    public SongTableModel(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public int getRowCount() {
        return songs.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Song song = songs.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> song.getTitle();
            case 1 -> ArtistDAO.getArtistByID(AlbumDAO.getAlbumByID(song.getAlbumID()).getArtistID()).getName();
            case 2 -> AlbumDAO.getAlbumByID(song.getAlbumID());
            case 3 -> GenreDAO.getGenreByID(AlbumDAO.getAlbumByID(song.getAlbumID()).getGenreID()).getName();
            case 4 -> AlbumDAO.getAlbumByID(song.getAlbumID()).getReleaseYear();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
