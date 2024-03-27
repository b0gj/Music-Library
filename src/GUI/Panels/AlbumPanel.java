package GUI.Panels;

import DAOs.AlbumDAO;
import GUI.TableModels.AlbumTableModel;

import javax.swing.*;
import java.awt.*;

public class AlbumPanel extends JPanel{
    AlbumTableModel albumTableModel = new AlbumTableModel(AlbumDAO.getAllAlbumsWithDetails());
    private JTable albumTable = new JTable(albumTableModel);
    private JButton addAlbumButton = new JButton("Add Album");

    public AlbumPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(albumTable), BorderLayout.CENTER);
        add(addAlbumButton, BorderLayout.SOUTH);
    }
}
