package Utils;

/**
 * Klasa obsługująca wyjątki błędów łączenia się z bazą danych
 */
public class MySqlCantConnectException extends Exception {
    public MySqlCantConnectException(Throwable e) {
        System.out.println("Łączenie z MariaDB nie powiodło się!\n" + e);
    }
}
