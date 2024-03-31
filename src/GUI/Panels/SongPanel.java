package GUI.Panels;

import DAOs.AlbumDAO;
import DAOs.ArtistDAO;
import DAOs.GenreDAO;
import DAOs.SongDAO;
import GUI.TableModels.SongTableModel;
import Model.Album;
import Model.Artist;
import Model.Genre;
import Model.Full.SongFull;
import Model.Song;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class SongPanel extends JPanel {
    SongTableModel songTableModel;
    private List<SongFull> allSongs;
    private final JTable songTable;
    private final JButton addSongButton = new JButton("Add Song");
    private final JButton deleteSongButton = new JButton("Delete Song/s");
    private final JButton updateSongButton = new JButton("Update Song");
    private final JButton searchSongButton = new JButton("Search Song");
    private final JButton showAllButton = new JButton("Show All");

    private final JTextField titleSearchField = new JTextField();
    private final JComboBox<Artist> artistSearchComboBox = new JComboBox<>();
    private final JComboBox<Genre> genreSearchComboBox = new JComboBox<>();


    public SongPanel() {
        allSongs = SongDAO.getAllSongsWithDetails();
        songTableModel = new SongTableModel(allSongs);
        songTable = new JTable(songTableModel);
        setLayout(new BorderLayout());
        add(new JScrollPane(songTable), BorderLayout.CENTER);
        songTable.getTableHeader().setReorderingAllowed(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addSongButton);
        buttonPanel.add(deleteSongButton);
        buttonPanel.add(updateSongButton);
        buttonPanel.add(searchSongButton);
        buttonPanel.add(showAllButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addSongButton.addActionListener(e -> showAddSongDialog());
        deleteSongButton.addActionListener(e -> deleteSelectedSongs());
        updateSongButton.addActionListener(e -> showUpdateSongDialog());
        searchSongButton.addActionListener(e -> showSearchDialog());
        showAllButton.addActionListener(e -> showAllSongs());

        deleteSongButton.setEnabled(false);
        updateSongButton.setEnabled(false);

        songTable.getSelectionModel().addListSelectionListener(e -> {
            boolean singleRowSelected = songTable.getSelectedRows().length == 1;
            updateSongButton.setEnabled(singleRowSelected);
            deleteSongButton.setEnabled(songTable.getSelectedRows().length > 0);
        });

        artistSearchComboBox.setModel(new DefaultComboBoxModel<>(new Vector<>(ArtistDAO.getAllArtists())));
        genreSearchComboBox.setModel(new DefaultComboBoxModel<>(new Vector<>(GenreDAO.getAllGenres())));

        artistSearchComboBox.insertItemAt(null, 0);
        genreSearchComboBox.insertItemAt(null, 0);
        artistSearchComboBox.setSelectedIndex(0);
        genreSearchComboBox.setSelectedIndex(0);

        titleSearchField.getDocument().addDocumentListener(createSearchDocumentListener());
        artistSearchComboBox.addActionListener(e -> filterSongs());
        genreSearchComboBox.addActionListener(e -> filterSongs());
    }

    private void showAddSongDialog() {
        JTextField titleField = new JTextField();
        JComboBox<Album> albumComboBox = new JComboBox<>(new Vector<>(AlbumDAO.getAllAlbums()));

        final JComponent[] inputs = new JComponent[]{
                new JLabel("Title"), titleField,
                new JLabel("Album"), albumComboBox
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Add Song", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            Album album = (Album) albumComboBox.getSelectedItem();

            if (!title.isEmpty() && album != null) {
                Song song = new Song(title, album.getID());
                SongDAO.addSong(song);
                refreshSongTable();
            } else {
                JOptionPane.showMessageDialog(null, "Title cannot be empty and an album must be selected.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void deleteSelectedSongs() {
        int[] selectedRows = songTable.getSelectedRows();
        StringBuilder summary = new StringBuilder("Deleted Songs:\n");

        for (int selectedRow : selectedRows) {
            int rowIndex = songTable.convertRowIndexToModel(selectedRow);
            int songId = songTableModel.getSongIdAtRow(rowIndex);
            SongDAO.deleteSong(songId);
            summary.append(songTableModel.getValueAt(rowIndex, 0)).append("\n");
        }

        refreshSongTable();

        JOptionPane.showMessageDialog(null, summary.toString(), "Songs Deleted", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showUpdateSongDialog() {
        int selectedRow = songTable.getSelectedRow();
        int songId = ((SongTableModel) songTable.getModel()).getSongIdAtRow(songTable.convertRowIndexToModel(selectedRow));
        Song existingSong = SongDAO.getSongByID(songId);

        JTextField titleField = new JTextField(existingSong.getTitle());
        JComboBox<Album> albumComboBox = new JComboBox<>(new Vector<>(AlbumDAO.getAllAlbums()));
        albumComboBox.setSelectedItem(findAlbumById(albumComboBox, existingSong.getAlbumID()));

        final JComponent[] inputs = new JComponent[]{
                new JLabel("Title"), titleField,
                new JLabel("Album"), albumComboBox
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Update Song", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            Album album = (Album) albumComboBox.getSelectedItem();

            if (!title.isEmpty() && album != null) {
                Song song = new Song(songId, title, album.getID());
                SongDAO.updateSong(song);
                refreshSongTable();
            } else {
                JOptionPane.showMessageDialog(null, "Title cannot be empty and an album must be selected.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Album findAlbumById(JComboBox<Album> comboBox, int albumId) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Album album  = comboBox.getItemAt(i);
            if (album.getID() == albumId) {
                return album;
            }
        }
        return null;
    }

    private void showSearchDialog() {
        JPanel searchPanel = new JPanel(new GridLayout(3, 2));
        searchPanel.add(new JLabel("Title:"));
        searchPanel.add(titleSearchField);
        searchPanel.add(new JLabel("Artist:"));
        searchPanel.add(artistSearchComboBox);
        searchPanel.add(new JLabel("Genre:"));
        searchPanel.add(genreSearchComboBox);

        JDialog searchDialog = new JDialog();
        searchDialog.setTitle("Search Songs");
        searchDialog.setContentPane(searchPanel);
        searchDialog.pack();
        searchDialog.setLocationRelativeTo(null);
        searchDialog.setVisible(true);
    }


    private void filterSongs() {
        String titleText = titleSearchField.getText().toLowerCase();
        Artist selectedArtist = (Artist) artistSearchComboBox.getSelectedItem();
        Genre selectedGenre = (Genre) genreSearchComboBox.getSelectedItem();

        List<SongFull> filteredSongs = allSongs.stream()
                .filter(song -> song.getTitle().toLowerCase().contains(titleText))
                .filter(song -> selectedArtist == null || song.getArtist().equals(selectedArtist.getName()))
                .filter(song -> selectedGenre == null || song.getGenre().equals(selectedGenre.getName()))
                .collect(Collectors.toList());

        songTableModel.setSongs(filteredSongs);
        songTableModel.fireTableDataChanged();
    }

    private DocumentListener createSearchDocumentListener() {
        return new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { filterSongs(); }
            public void removeUpdate(DocumentEvent e) { filterSongs(); }
            public void insertUpdate(DocumentEvent e) { filterSongs(); }
        };
    }

    private void showAllSongs() {
        songTableModel.setSongs(allSongs);
        songTableModel.fireTableDataChanged();
    }

    private void refreshSongTable() {
        songTableModel = new SongTableModel(SongDAO.getAllSongsWithDetails());
        songTable.setModel(songTableModel);
        songTableModel.fireTableDataChanged();
    }

}
