package Utils;

import java.io.IOException;

/**
 * Klasa obsługująca wyjątki błędów odczytu i zapisu
 */
public class MyIOException extends IOException {
    public MyIOException(IOException e) {
        System.out.println("Błąd odczytu lub zapisu!\n" + e.getMessage() + "\n" + e.getCause());
    }
}
