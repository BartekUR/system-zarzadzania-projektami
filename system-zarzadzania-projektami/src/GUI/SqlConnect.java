package GUI;

import java.sql.*;

/**
 * Klasa obsługująca połączenie z bazą danych
 */

public class SqlConnect {

    private static final String pass = "pass";
    private static final String user = "root";
    private static Connection conn = null;

    public void open() throws MySqlCantConnectException {
        try {
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306?user=" + user + "&password=" + pass);
            System.out.println("Łączenie z MariaDB powiodło się!");
        } catch (Exception e) {
            throw new MySqlCantConnectException(e);
        }
    }

    public void close() throws MySqlCantDisconnectException {
        try {
            conn.close();
            System.out.println("Rozłączenie z MariaDB powiodło sie!");
        } catch (Exception e) {
            throw new MySqlCantDisconnectException(e);
        }
    }

    public Connection getConn() {
        return conn;
    }
}
