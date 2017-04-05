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

public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("Uruchamianie aplikacji.");
        launch(args);
    }

    public void init() throws SQLException, IOException {
        SqlConnect sc = new SqlConnect();
        Connection conn = sc.open();

        if (conn != null) {
            ResultSet resultSet = conn.getMetaData().getCatalogs();
            boolean found = false;

            while (resultSet.next()) {
                if (resultSet.getString(1).contains("szp")) {
                    found = true;
                    System.out.println("Korzystam z istniejacej bazy.");
                }
            }
            if (!found) {
                BufferedReader br = new BufferedReader(new FileReader("db_init.sql"));
                String line;

                System.out.println("Inicjalizowanie bazy...");
                Statement stmt = conn.createStatement();
                while ((line = br.readLine()) != null) {
                    if (line.length() != 0)
                        stmt.executeUpdate(line);
                }
                conn.commit();
            }
            sc.close();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SzefGUI.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void stop() {
        System.out.println("Zakonczenie dzialania aplikacji.");
    }
}
