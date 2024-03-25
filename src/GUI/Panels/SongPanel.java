package GUI.Panels;

import javax.swing.*;
import java.awt.*;

public class SongPanel extends JPanel{
    private JTable songTable = new JTable();
    private JButton addSongButton = new JButton("Add Song");

    public SongPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(songTable), BorderLayout.CENTER);
        add(addSongButton, BorderLayout.SOUTH);
    }
}
