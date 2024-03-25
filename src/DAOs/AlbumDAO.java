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
    public static Album getAlbumByID(int albumID) {
        String sql = "SELECT * FROM Albums WHERE AlbumID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, albumID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Album(
                            rs.getInt("AlbumID"),
                            rs.getString("Title"),
                            rs.getInt("ArtistID"),
                            rs.getInt("ReleaseYear"),
                            rs.getInt("GenreID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Or a default Album object
    }

    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT AlbumID, Title, ArtistID, ReleaseYear, GenreID FROM Albums";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Album album = new Album(
                        rs.getInt("AlbumID"),
                        rs.getString("Title"),
                        rs.getInt("ArtistID"),
                        rs.getInt("ReleaseYear"),
                        rs.getInt("GenreID")
                );
                albums.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albums;
    }

    public void addAlbum(Album album) {
        String sql = "INSERT INTO Albums (Title, ArtistID, ReleaseYear, GenreID) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, album.getTitle());
            pstmt.setInt(2, album.getArtistID());
            pstmt.setInt(3, album.getReleaseYear());
            pstmt.setInt(4, album.getGenreID()); // Change to genreID
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAlbum(Album album) {
        String sql = "UPDATE Albums SET Title = ?, ArtistID = ?, ReleaseYear = ?, GenreID = ? WHERE AlbumID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, album.getTitle());
            pstmt.setInt(2, album.getArtistID());
            pstmt.setInt(3, album.getReleaseYear());
            pstmt.setInt(4, album.getGenreID()); // Change to genreID
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