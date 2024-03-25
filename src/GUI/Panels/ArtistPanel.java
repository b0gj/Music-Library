package GUI.Panels;

import javax.swing.*;
import java.awt.*;

public class ArtistPanel extends JPanel {
    private JTable artistTable = new JTable();
    private JButton addArtistButton = new JButton("Add Artist");

    public ArtistPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(artistTable), BorderLayout.CENTER);
        add(addArtistButton, BorderLayout.SOUTH);
    }
}
