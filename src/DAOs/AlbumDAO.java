    package DAOs;

    import DB.DBConnection;
    import Model.Album;
    import Model.Full.AlbumFull;

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
            return null;
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

        public static List<AlbumFull> getAllAlbumsWithDetails() {
            List<AlbumFull> albums = new ArrayList<>();
            String sql = "SELECT a.AlbumID, a.Title, ar.Name as ArtistName, g.Name as GenreName, COUNT(s.SongID) as SongsCount, a.ReleaseYear " +
                    "FROM Albums a " +
                    "JOIN Artists ar ON a.ArtistID = ar.ArtistID " +
                    "JOIN Genres g ON a.GenreID = g.GenreID " +
                    "LEFT JOIN Songs s ON a.AlbumID = s.AlbumID " +
                    "GROUP BY a.AlbumID";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    AlbumFull album = new AlbumFull(
                            rs.getInt("AlbumID"),
                            rs.getString("Title"),
                            rs.getString("ArtistName"),
                            rs.getString("GenreName"),
                            rs.getInt("SongsCount"),
                            rs.getInt("ReleaseYear")
                    );
                    albums.add(album);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return albums;
        }


        public static void addAlbum(Album album) {
            String sql = "INSERT INTO Albums (Title, ArtistID, ReleaseYear, GenreID) VALUES (?, ?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, album.getTitle());
                pstmt.setInt(2, album.getArtistID());
                pstmt.setInt(3, album.getReleaseYear());
                pstmt.setInt(4, album.getGenreID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static boolean deleteAlbum(int albumId) {
            String sql = "DELETE FROM Albums WHERE AlbumID = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, albumId);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        public static void updateAlbum(Album album) {
            String sql = "UPDATE Albums SET Title = ?, ArtistID = ?, ReleaseYear = ?, GenreID = ? WHERE AlbumID = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, album.getTitle());
                pstmt.setInt(2, album.getArtistID());
                pstmt.setInt(3, album.getReleaseYear());
                pstmt.setInt(4, album.getGenreID());
                pstmt.setInt(5, album.getID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }