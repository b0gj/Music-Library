package GUI.Panels;

import javax.swing.*;
import java.awt.*;

public class AlbumPanel extends JPanel{
    private JTable albumTable = new JTable();
    private JButton addAlbumButton = new JButton("Add Album");

    public AlbumPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(albumTable), BorderLayout.CENTER);
        add(addAlbumButton, BorderLayout.SOUTH);
    }
}
