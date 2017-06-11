package GUI;

public class MySqlQueryException extends Exception {
    public MySqlQueryException(Throwable e) {
        System.out.println("Błąd zapytania SQL!\n" + e);
    }
}
