package DAOs;

import DB.DBConnection;
import Model.Full.SongFull;
import Model.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDAO {

    public static List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT SongID, Title, AlbumID FROM Songs";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Song song = new Song(
                        rs.getInt("SongID"),
                        rs.getString("Title"),
                        rs.getInt("AlbumID")
                );
                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static List<SongFull> getAllSongsWithDetails() {
        List<SongFull> songs = new ArrayList<>();
        String sql = "SELECT s.Title, ar.Name as ArtistName, a.Title as AlbumTitle, g.Name as GenreName, a.ReleaseYear " +
                "FROM Songs s " +
                "JOIN Albums a ON s.AlbumID = a.AlbumID " +
                "JOIN Artists ar ON a.ArtistID = ar.ArtistID " +
                "JOIN Genres g ON a.GenreID = g.GenreID";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                SongFull song = new SongFull(
                        rs.getString("Title"),
                        rs.getString("ArtistName"),
                        rs.getString("AlbumTitle"),
                        rs.getString("GenreName"),
                        rs.getString("ReleaseYear")
                );
                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public void addSong(Song song) {
        String sql = "INSERT INTO Songs (Title, AlbumID) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, song.getTitle());
            pstmt.setInt(2, song.getAlbumID());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSong(Song song) {
        String sql = "UPDATE Songs SET Title = ?, AlbumID = ? WHERE SongID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, song.getTitle());
            pstmt.setInt(2, song.getAlbumID());
            pstmt.setInt(3, song.getID());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSong(int songId) {
        String sql = "DELETE FROM Songs WHERE SongID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, songId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
