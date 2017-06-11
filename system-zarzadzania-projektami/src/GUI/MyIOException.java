package GUI;

public class MyIOException extends Exception {
    public MyIOException(Throwable e) {
        System.out.println("Błąd odczytu lub zapisu!\n" + e);
    }
}
