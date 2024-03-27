package DAOs;

import DB.DBConnection;
import Model.Artist;
import Model.Full.ArtistFull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAO {

    public static Artist getArtistByID(int artistID) {
        String sql = "SELECT * FROM Artists WHERE ArtistID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, artistID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Artist(
                            rs.getInt("ArtistID"),
                            rs.getString("Name"),
                            rs.getInt("BirthYear")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Or a default Artist object
    }

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

    public List<ArtistFull> getAllArtistsWithDetails() {
        List<ArtistFull> artists = new ArrayList<>();
        String sql = "SELECT ar.Name, ar.BirthYear, COUNT(DISTINCT a.AlbumID) as AlbumsCount, COUNT(s.SongID) as SongsCount " +
                "FROM Artists ar " +
                "LEFT JOIN Albums a ON ar.ArtistID = a.ArtistID " +
                "LEFT JOIN Songs s ON a.AlbumID = s.AlbumID " +
                "GROUP BY ar.ArtistID";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ArtistFull artist = new ArtistFull(
                        rs.getString("Name"),
                        rs.getInt("AlbumsCount"),
                        rs.getInt("SongsCount"),
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