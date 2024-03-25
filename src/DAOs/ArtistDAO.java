package DAOs;

import DB.DBConnection;
import Model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAO {

    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        String sql = "SELECT ArtistID, Name, BirthYear FROM Artists";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Artist artist = new Artist(
                        rs.getInt("ArtistID"),
                        rs.getString("Name"),
                        rs.getInt("BirthYear")
                );
                artists.add(artist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artists;
    }

    public void addArtist(Artist artist) {
        String sql = "INSERT INTO Artists (Name, BirthYear) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, artist.getName());
            pstmt.setInt(2, artist.getBirthYear());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateArtist(Artist artist) {
        String sql = "UPDATE Artists SET Name = ?, BirthYear = ? WHERE ArtistID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, artist.getName());
            pstmt.setInt(2, artist.getBirthYear());
            pstmt.setInt(3, artist.getID());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteArtist (int artistId){
        String sql = "DELETE FROM Artists WHERE ArtistID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, artistId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}