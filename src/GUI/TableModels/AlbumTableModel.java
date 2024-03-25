package GUI.TableModels;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import DAOs.ArtistDAO;
import Model.Album;
import DAOs.GenreDAO;

public class AlbumTableModel extends AbstractTableModel {
    private final List<Album> albums;
    private final String[] columnNames = {"Title", "Artist", "Genre", "Release Year"};

    public AlbumTableModel(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public int getRowCount() {
        return albums.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Album album = albums.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> album.getTitle();
            case 1 -> ArtistDAO.getArtistByID(album.getArtistID()).getName();
            case 2 -> GenreDAO.getGenreByID(album.getGenreID()).getName();
            case 3 -> album.getReleaseYear();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
