package Utils;

/**
 * Klasa zawierająca metody pomocnicze
 */
public class Checks {
    /**
     * Metoda testująca zgodność hasła
     * @param a String
     * @param b String
     * @return true jeśli się zgadza
     */
    public boolean checkPass(String a, String b) {
        if (b == "") return false;

        if (a.compareTo(b) == 0)
            return true;
        else
            return false;
    }
}
