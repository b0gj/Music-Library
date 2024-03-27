package GUI.TableModels;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import Model.Full.SongFull;

public class SongTableModel extends AbstractTableModel {
    private final List<SongFull> songs;
    private final String[] columnNames = {"Title", "Artist", "Album", "Genre", "Release Year"};

    public SongTableModel(List<SongFull> songs) {
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
        SongFull song = songs.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> song.getTitle();
            case 1 -> song.getArtist();
            case 2 -> song.getAlbum();
            case 3 -> song.getGenre();
            case 4 -> song.getReleaseYear();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
