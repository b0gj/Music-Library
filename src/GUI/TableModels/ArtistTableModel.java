package GUI.TableModels;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import Model.Artist;

public class ArtistTableModel extends AbstractTableModel {
    private final List<Artist> artists;
    private final String[] columnNames = {"Name", "Birth Year"};

    public ArtistTableModel(List<Artist> artists) {
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
        Artist artist = artists.get(rowIndex);
        switch (columnIndex) {
            case 0: return artist.getName();
            case 1: return artist.getBirthYear();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
