package GUI.Panels;

import DAOs.SongDAO;
import GUI.TableModels.SongTableModel;

import javax.swing.*;
import java.awt.*;

public class SongPanel extends JPanel{
    SongTableModel songTableModel = new SongTableModel(SongDAO.getAllSongsWithDetails());
    private JTable songTable = new JTable(songTableModel);
    private JButton addSongButton = new JButton("Add Song");

    public SongPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(songTable), BorderLayout.CENTER);
        add(addSongButton, BorderLayout.SOUTH);
    }
}
