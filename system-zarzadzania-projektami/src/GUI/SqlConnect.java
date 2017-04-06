package GUI;

import java.sql.*;

public class SqlConnect {

    private static final String pass = "pass";
    private static final String user = "root";
    public static Connection conn = null;

    public boolean open() {
        try {
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306?user=" + user + "&password=" + pass);
            System.out.println("Laczenie z MariaDB powiodlo sie!");
            return true;
        } catch (Exception e) {
            System.out.println("Laczenie z MariaDB nie powiodlo sie!\n");
            e.printStackTrace();
            return false;
        }
    }

    public boolean close() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Rozlaczenie z MariaDB powiodlo sie!");
                return true;
            } catch (Exception e) {
                System.out.println("Rozlaczenie z MariaDB nie powiodlo sie!\n");
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
