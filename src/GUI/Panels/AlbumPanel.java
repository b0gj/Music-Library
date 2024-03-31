package GUI.Panels;

import DAOs.AlbumDAO;
import DAOs.ArtistDAO;
import DAOs.GenreDAO;
import GUI.ChangeSummaryDialog;
import GUI.TableModels.AlbumTableModel;
import Model.Album;
import Model.Artist;
import Model.Genre;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Vector;

public class AlbumPanel extends JPanel {
    AlbumTableModel albumTableModel = new AlbumTableModel(AlbumDAO.getAllAlbumsWithDetails());
    private final JTable albumTable = new JTable(albumTableModel);
    private final JButton addAlbumButton = new JButton("Add Album");
    private final JButton deleteAlbumButton = new JButton("Delete Album/s");
    private final JButton updateAlbumButton = new JButton("Update Album");

    public AlbumPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(albumTable), BorderLayout.CENTER);
        albumTable.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        albumTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addAlbumButton);
        buttonPanel.add(deleteAlbumButton);
        buttonPanel.add(updateAlbumButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addAlbumButton.addActionListener(e -> showAddAlbumDialog());
        deleteAlbumButton.addActionListener(e -> deleteSelectedAlbums());
        updateAlbumButton.addActionListener(e -> showUpdateAlbumDialog());

        deleteAlbumButton.setEnabled(false);
        updateAlbumButton.setEnabled(false);

        albumTable.getSelectionModel().addListSelectionListener(e -> {
            boolean singleRowSelected = albumTable.getSelectedRows().length == 1;
            updateAlbumButton.setEnabled(singleRowSelected);
            deleteAlbumButton.setEnabled(albumTable.getSelectedRows().length > 0);
        });
    }

    private void showAddAlbumDialog() {
        JTextField titleField = new JTextField();
        JComboBox<Artist> artistComboBox = new JComboBox<>(new Vector<>(ArtistDAO.getAllArtists()));
        JComboBox<Genre> genreComboBox = new JComboBox<>(new Vector<>(GenreDAO.getAllGenres()));
        JTextField yearField = new JTextField();

        final JComponent[] inputs = new JComponent[]{
                new JLabel("Title"), titleField,
                new JLabel("Artist"), artistComboBox,
                new JLabel("Genre"), genreComboBox,
                new JLabel("Release Year"), yearField
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Add Album", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText().trim();
                Artist artist = (Artist) artistComboBox.getSelectedItem();
                Genre genre = (Genre) genreComboBox.getSelectedItem();
                int year = Integer.parseInt(yearField.getText().trim());

                Album album = new Album(title, artist.getID(), year, genre.getGenreID());
                AlbumDAO.addAlbum(album);
                refreshAlbumTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Release Year must be a valid number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void deleteSelectedAlbums() {
        int[] selectedRows = albumTable.getSelectedRows();
        StringBuilder summary = new StringBuilder();
        for (int selectedRow : selectedRows) {
            int albumId = albumTableModel.getAlbumIdAtRow(albumTable.convertRowIndexToModel(selectedRow));
            if (AlbumDAO.deleteAlbum(albumId)) {
                summary.append("Deleted album ID: ").append(albumId).append("\n");
            } else {
                summary.append("Could not delete album ID: ").append(albumId).append(". It may have songs or does not exist.\n");
            }
        }
        new ChangeSummaryDialog(null, "Delete Summary", summary.toString());
        refreshAlbumTable();
    }


    private void showUpdateAlbumDialog() {
        int selectedRow = albumTable.getSelectedRow();
        int albumId = albumTableModel.getAlbumIdAtRow(albumTable.convertRowIndexToModel(selectedRow));
        Album existingAlbum = AlbumDAO.getAlbumByID(albumId);

        JTextField titleField = new JTextField(existingAlbum.getTitle());

        JComboBox<Artist> artistComboBox = new JComboBox<>(new Vector<>(ArtistDAO.getAllArtists()));
        artistComboBox.setSelectedItem(findArtistById(artistComboBox, existingAlbum.getArtistID()));

        JComboBox<Genre> genreComboBox = new JComboBox<>(new Vector<>(GenreDAO.getAllGenres()));
        genreComboBox.setSelectedItem(findGenreById(genreComboBox, existingAlbum.getGenreID()));

        JTextField yearField = new JTextField(String.valueOf(existingAlbum.getReleaseYear()));


        final JComponent[] inputs = new JComponent[]{
                new JLabel("Title"), titleField,
                new JLabel("Artist"), artistComboBox,
                new JLabel("Genre"), genreComboBox,
                new JLabel("Release Year"), yearField
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Update Album", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText().trim();
                Artist artist = (Artist) artistComboBox.getSelectedItem();
                Genre genre = (Genre) genreComboBox.getSelectedItem();
                int year = Integer.parseInt(yearField.getText().trim());

                Album updatedAlbum = new Album(albumId, title, artist.getID(), year, genre.getGenreID());
                AlbumDAO.updateAlbum(updatedAlbum);
                refreshAlbumTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Release Year must be a valid number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Artist findArtistById(JComboBox<Artist> comboBox, int artistId) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Artist artist = comboBox.getItemAt(i);
            if (artist.getID() == artistId) {
                return artist;
            }
        }
        return null;
    }

    private Genre findGenreById(JComboBox<Genre> comboBox, int genreId) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Genre genre = comboBox.getItemAt(i);
            if (genre.getGenreID() == genreId) {
                return genre;
            }
        }
        return null;
    }

    private void refreshAlbumTable() {
        albumTableModel = new AlbumTableModel(AlbumDAO.getAllAlbumsWithDetails());
        albumTable.setModel(albumTableModel);
    }

}
