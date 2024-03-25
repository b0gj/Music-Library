package App;

import GUI.MusicCollectionFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        ArtistDAO artistDAO = new ArtistDAO();
//        Artist newArtist = new Artist(0, "New Artist Name", 1985); // assuming Artist constructor takes ID, Name, and BirthYear
//        artistDAO.addArtist(newArtist);

        SwingUtilities.invokeLater(MusicCollectionFrame::new);
    }
}