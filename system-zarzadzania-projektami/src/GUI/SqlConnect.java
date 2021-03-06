package GUI;

import Utils.MySqlCantConnectException;
import Utils.MySqlCantDisconnectException;

import java.sql.*;

/**
 * Klasa obsługująca połączenie z bazą danych
 */
public class SqlConnect {

    private static final String pass = "pass";
    private static final String user = "root";
    private static Connection conn = null;

    /**
     * Metoda otwierająca połączenie z bazą danych
     * @throws MySqlCantConnectException
     */
    public void open() throws MySqlCantConnectException {
        try {
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306?user=" + user + "&password=" + pass);
            System.out.println("Łączenie z MariaDB powiodło się!");
        } catch (SQLException e) {
            throw new MySqlCantConnectException(e);
        }
    }

    /**
     * Metoda zamykająca połączenie z bazą danych
     * @throws MySqlCantDisconnectException
     */
    public void close() throws MySqlCantDisconnectException {
        try {
            conn.close();
            System.out.println("Rozłączenie z MariaDB powiodło sie!");
        } catch (SQLException e) {
            throw new MySqlCantDisconnectException(e);
        }
    }

    /**
     * Metoda zwracająca połączenie
     * @return connection
     */
    public Connection getConn() {
        return conn;
    }
}
