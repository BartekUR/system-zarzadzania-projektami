package GUI;

public class MySqlCantDisconnectException extends Exception {
    public MySqlCantDisconnectException(Throwable e) {
        System.out.println("Rozłączanie z MariaDB nie powiodło się!\n" + e);
    }
}
