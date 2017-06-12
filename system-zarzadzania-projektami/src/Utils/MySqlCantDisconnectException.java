package Utils;

import java.sql.SQLException;

/**
 * Klasa obsługująca wyjątki błędów rozłączania się z bazą danych
 */
public class MySqlCantDisconnectException extends SQLException {
    public MySqlCantDisconnectException(SQLException e) {
        System.out.println("Rozłączanie z MariaDB nie powiodło się!\n" + e.getMessage());
    }
}
