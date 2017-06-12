package Utils;

/**
 * Klasa obsługująca wyjątki błędów odczytu i zapisu
 */
public class MyIOException extends Exception {
    public MyIOException(Throwable e) {
        System.out.println("Błąd odczytu lub zapisu!\n" + e);
    }
}
