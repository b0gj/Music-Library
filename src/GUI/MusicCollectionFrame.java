package GUI;

import javax.swing.*;
import java.awt.*;

public class MusicCollectionFrame extends JFrame {

    private JTabbedPane tabbedPane = new JTabbedPane();

    // Tabs
    private JPanel artistPanel = new JPanel(new BorderLayout());
    private JPanel albumPanel = new JPanel(new BorderLayout());
    private JPanel songPanel = new JPanel(new BorderLayout());

    // Artist tab components
    private JTable artistTable = new JTable();
    private JButton addArtistButton = new JButton("Add Artist");

    // Album tab components
    private JTable albumTable = new JTable();
    private JButton addAlbumButton = new JButton("Add Album");

    // Song tab components
    private JTable songTable = new JTable();
    private JButton addSongButton = new JButton("Add Song");

    public MusicCollectionFrame() {
        setTitle("Music Collection");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Artist Panel Setup
        artistPanel.add(new JScrollPane(artistTable), BorderLayout.CENTER);
        artistPanel.add(addArtistButton, BorderLayout.SOUTH);

        // Album Panel Setup
        albumPanel.add(new JScrollPane(albumTable), BorderLayout.CENTER);
        albumPanel.add(addAlbumButton, BorderLayout.SOUTH);

        // Song Panel Setup
        songPanel.add(new JScrollPane(songTable), BorderLayout.CENTER);
        songPanel.add(addSongButton, BorderLayout.SOUTH);

        // Adding Tabs
        tabbedPane.addTab("Artists", artistPanel);
        tabbedPane.addTab("Albums", albumPanel);
        tabbedPane.addTab("Songs", songPanel);

        // Adding the tabbed pane to the frame
        add(tabbedPane);

        // Display the JFrame
        setVisible(true);
    }
}
