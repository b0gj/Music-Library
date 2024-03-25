package GUI;

import GUI.Panels.ArtistPanel;
import GUI.Panels.AlbumPanel;
import GUI.Panels.SongPanel;

import javax.swing.*;
import java.awt.*;

public class MusicCollectionFrame extends JFrame {

    private JTabbedPane tabbedPane = new JTabbedPane();
    public MusicCollectionFrame() {
        setTitle("Music Collection");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Adding Tabs
        tabbedPane.addTab("Songs", new SongPanel());
        tabbedPane.addTab("Artists", new ArtistPanel());
        tabbedPane.addTab("Albums", new AlbumPanel());

        // Adding the tabbed pane to the frame
        add(tabbedPane);

        // Display the JFrame
        setVisible(true);
    }
}
