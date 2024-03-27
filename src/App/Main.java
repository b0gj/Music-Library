package App;

import GUI.MusicCollectionFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MusicCollectionFrame::new);
    }
}