package DAOs;

import DB.DBConnection;
import Model.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO {

    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM Albums";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Album album = new Album(
                        rs.getInt("AlbumID"),
                        rs.getString("Title"),
                        rs.getInt("ArtistID"),
                        rs.getInt("ReleaseYear"),
                        rs.getString("Genre")
                );
                albums.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albums;
    }

    public void addAlbum(Album album) {
        String sql = "INSERT INTO Albums (Title, ArtistID, ReleaseYear, Genre) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, album.getTitle());
            pstmt.setInt(2, album.getArtistID());
            pstmt.setInt(3, album.getReleaseYear());
            pstmt.setString(4, album.getGenre());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAlbum(Album album) {
        String sql = "UPDATE Albums SET Title = ?, ArtistID = ?, ReleaseYear = ?, Genre = ? WHERE AlbumID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, album.getTitle());
            pstmt.setInt(2, album.getArtistID());
            pstmt.setInt(3, album.getReleaseYear());
            pstmt.setString(4, album.getGenre());
            pstmt.setInt(5, album.getID());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAlbum(int albumId) {
        String sql = "DELETE FROM Albums WHERE AlbumID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, albumId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}