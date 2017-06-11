package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * Klasa Main
 */

public class Main extends Application {

    private SqlConnect sc = new SqlConnect();

    public static void main(String[] args) {
        System.out.println("Uruchamianie aplikacji.");
        launch(args);
    }

    /**
     * Połaczenie z bozą danych
     */

    public void init() throws MySqlCantConnectException, MySqlQueryException, MyIOException {
        sc.open();
        Connection conn = sc.getConn();
        boolean found;
        try {
            ResultSet resultSet = conn.getMetaData().getCatalogs();
            found = false;

            while (resultSet.next()) {
                if (resultSet.getString(1).contains("szp")) {
                    found = true;
                    System.out.println("Znaleziono bazę. Korzystam z istniejącej bazy.");
                }
            }
        } catch (Exception e) {
            throw new MySqlQueryException(e);
        }
        if (!found) {
            String line;

            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader("./system-zarzadzania-projektami/db_init.sql"));
            } catch(Exception e) {
                throw new MyIOException(e);
            }
            System.out.println("Nie znaleziono bazy. Inicjalizuję nową bazę...");
            try {
                Statement stmt = conn.createStatement();
                while ((line = br.readLine()) != null) {
                    if (line.length() != 0)
                        stmt.executeUpdate(line);
                }
                conn.commit();
            } catch(Exception e) {
                throw new MySqlQueryException(e);
            }
        }
    }

    /**
     * Metoda do włączania okna logowania
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Logowanie.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("System zarządzania projektami 1.0 - Logowanie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Metoda do wyłączania okna
     */

    public void stop() throws MySqlCantDisconnectException {
        sc.close();
        System.out.println("Zamykanie aplikacji.");
    }
}
