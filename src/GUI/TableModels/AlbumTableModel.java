package GUI.TableModels;

import Model.Full.AlbumFull;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AlbumTableModel extends AbstractTableModel {
    private final List<AlbumFull> albums;
    private final String[] columnNames = {"Title", "Artist", "Genre", "Songs Count", "Release Year"}; // Example column names

    public AlbumTableModel(List<AlbumFull> albums) {
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
        AlbumFull album = albums.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> album.getTitle();
            case 1 -> album.getArtist();
            case 2 -> album.getGenre();
            case 3 -> album.getSongsCount();
            case 4 -> album.getReleaseYear();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public int getAlbumIdAtRow(int rowIndex) {
        return albums.get(rowIndex).getID();
    }
}
