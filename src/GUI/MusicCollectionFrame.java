package GUI;

import GUI.Panels.ArtistPanel;
import GUI.Panels.AlbumPanel;
import GUI.Panels.SongPanel;

import javax.swing.*;

public class MusicCollectionFrame extends JFrame {

    private JTabbedPane tabbedPane = new JTabbedPane();
    public MusicCollectionFrame() {
        setTitle("Music Collection");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabbedPane.addTab("Artists", new ArtistPanel());
        tabbedPane.addTab("Albums", new AlbumPanel());
        tabbedPane.addTab("Songs", new SongPanel());

        add(tabbedPane);

        setVisible(true);
    }
}
