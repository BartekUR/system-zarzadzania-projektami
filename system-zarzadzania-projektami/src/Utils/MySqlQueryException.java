package Utils;

import java.sql.SQLException;

/**
 * Klasa obsługująca wyjątki błędów zapytan SQL
 */
public class MySqlQueryException extends SQLException {
    public MySqlQueryException(SQLException e) {
        System.out.println("Błąd zapytania SQL!\n" + e.getMessage());
    }
}
