package GUI.Panels;

import DAOs.ArtistDAO;
import GUI.TableModels.ArtistTableModel;

import javax.swing.*;
import java.awt.*;

public class ArtistPanel extends JPanel {
    ArtistTableModel artistTableModel = new ArtistTableModel(ArtistDAO.getAllArtistsWithDetails());
    private JTable artistTable = new JTable(artistTableModel);
    private JButton addArtistButton = new JButton("Add Artist");

    public ArtistPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(artistTable), BorderLayout.CENTER);
        add(addArtistButton, BorderLayout.SOUTH);
    }
}
