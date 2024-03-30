package GUI.TableModels;

import Model.Full.ArtistFull;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ArtistTableModel extends AbstractTableModel {
    private final List<ArtistFull> artists;
    private final String[] columnNames = {"Name", "Albums Count", "Songs Count", "Birth Year"};

    public ArtistTableModel(List<ArtistFull> artists) {
        this.artists = artists;
    }

    @Override
    public int getRowCount() {
        return artists.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArtistFull artist = artists.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> artist.getName();
            case 1 -> artist.getAlbumsCount();
            case 2 -> artist.getSongsCount();
            case 3 -> artist.getBirthYear();
            default -> null;
        };
    }

    public int getArtistIdAtRow(int rowIndex) {
        return artists.get(rowIndex).getID();
    }

    public int getArtistBirthYearAtRow(int rowIndex) {
        return artists.get(rowIndex).getBirthYear();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
