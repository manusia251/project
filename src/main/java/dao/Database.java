package dao;
//import library untuk database nya, di sini pakai sqlite
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class Database {
    //ini untuk menentukan lokasi dari data basenya nanti
    private static final Path DB_PATH =
            
            Path.of("data", "spp.db").toAbsolutePath();

    private static final String URL =
            "jdbc:sqlite:" + DB_PATH;

    // Membuat koneksi ke database
    public static Connection getConnection() throws SQLException {
        // coba buat file database
        try {
            Files.createDirectories(DB_PATH.getParent());
            // kalau gagal keluarkan ini
        } catch (IOException e) {
            throw new SQLException("Gagal membuat folder database", e);
        }

        return DriverManager.getConnection(URL);
    }

    // Membuat tabel ketika aplikasi pertama kali dijalankan
    public static void initialize() throws SQLException {

        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL
            )
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

            // Akun demo untuk pengujian
            stmt.executeUpdate("""
                INSERT OR IGNORE INTO users(username, password)
                VALUES ('admin', 'admin123')
            """);
        }
    }
}