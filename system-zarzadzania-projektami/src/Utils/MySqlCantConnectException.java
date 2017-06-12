package Utils;

import java.sql.SQLException;

/**
 * Klasa obsługująca wyjątki błędów łączenia się z bazą danych
 */
public class MySqlCantConnectException extends SQLException {
    public MySqlCantConnectException(SQLException e) {
        System.out.println("Łączenie z MariaDB nie powiodło się!\n" + e.getMessage());
    }
}
