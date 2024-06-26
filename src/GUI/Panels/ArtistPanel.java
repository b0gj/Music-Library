package GUI.Panels;

import DAOs.ArtistDAO;
import GUI.ChangeSummaryDialog;
import GUI.TableModels.ArtistTableModel;
import Model.Artist;

import javax.swing.*;
import java.awt.*;

public class ArtistPanel extends JPanel {
    ArtistTableModel artistTableModel = new ArtistTableModel(ArtistDAO.getAllArtistsWithDetails());
    private final JTable artistTable = new JTable(artistTableModel);
    private final  JButton addArtistButton = new JButton("Add Artist");
    private final JButton deleteArtistButton = new JButton("Delete Artist/s");
    private final JButton updateArtistButton = new JButton("Update Artist");

    public ArtistPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(artistTable), BorderLayout.CENTER);
        artistTable.getTableHeader().setReorderingAllowed(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addArtistButton);
        buttonPanel.add(deleteArtistButton);
        buttonPanel.add(updateArtistButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addArtistButton.addActionListener(e -> showAddArtistDialog());

        deleteArtistButton.addActionListener(e -> deleteSelectedArtist());

        updateArtistButton.addActionListener(e -> updateSelectedArtist());

        deleteArtistButton.setEnabled(false);
        updateArtistButton.setEnabled(false);

        artistTable.getSelectionModel().addListSelectionListener(e -> {
            boolean singleRowSelected = artistTable.getSelectedRows().length == 1;
            updateArtistButton.setEnabled(singleRowSelected);
            deleteArtistButton.setEnabled(artistTable.getSelectedRows().length > 0);
        });
    }

    private void showAddArtistDialog() {
        JTextField nameField = new JTextField();
        JTextField birthYearField = new JTextField();
        final JComponent[] inputs = new JComponent[]{
                new JLabel("Name"),
                nameField,
                new JLabel("Birth Year"),
                birthYearField
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Add Artist", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                int birthYear = Integer.parseInt(birthYearField.getText().trim());
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name cannot be empty", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ArtistDAO.addArtist(new Artist(name, birthYear));
                refreshArtistTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Birth Year must be a valid number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteSelectedArtist() {
        StringBuilder deletedArtists = new StringBuilder();
        StringBuilder nonDeletedArtists = new StringBuilder();

        int[] selectedRows = artistTable.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "No artist selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected artist(s)?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            for (int selectedRowIndex : selectedRows) {
                int artistId = ((ArtistTableModel)artistTable.getModel()).getArtistIdAtRow(artistTable.convertRowIndexToModel(selectedRowIndex));
                String artistName = (String) artistTable.getModel().getValueAt(artistTable.convertRowIndexToModel(selectedRowIndex), 0);
                if (ArtistDAO.deleteArtist(artistId)) {
                    deletedArtists.append(artistName).append("\n");
                } else {
                    nonDeletedArtists.append(artistName).append("\n");
                }
            }
            refreshArtistTable();

            StringBuilder summaryMessage = new StringBuilder();
            if (deletedArtists.length() > 0) {
                summaryMessage.append("Deleted:\n").append(deletedArtists);
            }
            if (nonDeletedArtists.length() > 0) {
                if (summaryMessage.length() > 0) summaryMessage.append("\n");
                summaryMessage.append("Not able to delete (Delete their albums first):\n").append(nonDeletedArtists);
            }

            new ChangeSummaryDialog(null, "Changes Summary", summaryMessage.toString());
        }
    }

    private void updateSelectedArtist() {
        int[] selectedRows = artistTable.getSelectedRows();
        if (selectedRows.length != 1) {
            JOptionPane.showMessageDialog(this, "Please select exactly one artist to update.", "Invalid Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRowIndex = selectedRows[0];
        int artistId = ((ArtistTableModel) artistTable.getModel()).getArtistIdAtRow(artistTable.convertRowIndexToModel(selectedRowIndex));
        String artistName = (String) artistTable.getModel().getValueAt(artistTable.convertRowIndexToModel(selectedRowIndex), 0);
        int birthYear = ((ArtistTableModel) artistTable.getModel()).getArtistBirthYearAtRow(artistTable.convertRowIndexToModel(selectedRowIndex));

        showUpdateArtistDialog(artistId, artistName, birthYear);
    }

    private void showUpdateArtistDialog(int artistId, String name, int birthYear) {
        JTextField nameField = new JTextField(name);
        JTextField birthYearField = new JTextField(String.valueOf(birthYear));
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Name"),
                nameField,
                new JLabel("Birth Year"),
                birthYearField
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Update Artist", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String newName = nameField.getText().trim();
                int newBirthYear = Integer.parseInt(birthYearField.getText().trim());
                if (newName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name cannot be empty", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArtistDAO.updateArtist(new Artist(artistId, newName, newBirthYear));
                refreshArtistTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Birth Year must be a valid number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void refreshArtistTable() {
        artistTableModel = new ArtistTableModel(ArtistDAO.getAllArtistsWithDetails());
        artistTable.setModel(artistTableModel);
        artistTableModel.fireTableDataChanged();
    }
}
