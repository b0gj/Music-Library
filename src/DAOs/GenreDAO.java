package DAOs;

import DB.DBConnection;
import Model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {

    public static Genre getGenreByID(int genreID) {
        String sql = "SELECT * FROM Genres WHERE GenreID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, genreID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Genre(
                            rs.getInt("GenreID"),
                            rs.getString("Name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM Genres";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Genre genre = new Genre(
                        rs.getInt("GenreID"),
                        rs.getString("Name")
                );
                genres.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public void addGenre(Genre genre) {
        String sql = "INSERT INTO Genres (Name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, genre.getName());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        genre.setGenreID(rs.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGenre(Genre genre) {
        String sql = "UPDATE Genres SET Name = ? WHERE GenreID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, genre.getName());
            pstmt.setInt(2, genre.getGenreID());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGenre(int genreId) {
        String sql = "DELETE FROM Genres WHERE GenreID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, genreId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
