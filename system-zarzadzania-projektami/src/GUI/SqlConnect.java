package GUI;

import java.sql.*;

public class SqlConnect {

    Connection conn = null;
    private static final String pass = "pass";
    private static final String user = "root";

    public Connection open() {
        try {
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306?user=" + user + "&password=" + pass);
            System.out.println("Laczenie z baza powiodlo sie!");
            return conn;
        } catch (Exception e) {
            System.out.println("Laczenie z baza nie powiodlo sie!\n");
            e.printStackTrace();
            return null;
        }
    }

    public boolean close() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Rozlaczanie z baza powiodlo sie!");
                return true;
            } catch (Exception e) {
                System.out.println("Rozlaczanie z baza nie powiodlo sie!\n");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
