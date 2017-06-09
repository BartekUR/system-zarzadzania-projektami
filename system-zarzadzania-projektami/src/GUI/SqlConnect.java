package GUI;

import java.sql.*;

/**
 * Klasa obsługująca połączenie z bazą danych
 */

public class SqlConnect {

    private static final String pass = "pass";
    private static final String user = "root";
    private static Connection conn = null;

    public boolean open() {
        try {
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306?user=" + user + "&password=" + pass);
            System.out.println("Łączenie z MariaDB powiodło się!");
            return true;
        } catch (Exception e) {
            System.out.println("Łączenie z MariaDB nie powiodło się!\n");
            e.printStackTrace();
            return false;
        }
    }

    public boolean close() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Rozłączenie z MariaDB powiodło sie!");
                return true;
            } catch (Exception e) {
                System.out.println("Rozłączenie z MariaDB nie powiodło sie!\n");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public Connection getConn() {
        return conn;
    }
}
