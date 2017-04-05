package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;

public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("Uruchamianie aplikacji.");
        launch(args);
    }

    public void init() {
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
