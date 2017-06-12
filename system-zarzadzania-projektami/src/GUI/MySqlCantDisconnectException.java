package GUI;

/**
 * Klasa obsługująca wyjątki błędów rozłączania się z bazą danych
 */
public class MySqlCantDisconnectException extends Exception {
    public MySqlCantDisconnectException(Throwable e) {
        System.out.println("Rozłączanie z MariaDB nie powiodło się!\n" + e);
    }
}
