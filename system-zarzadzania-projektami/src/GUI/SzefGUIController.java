package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Michal on 2017-03-22.
 */
public class SzefGUIController implements Initializable {

    @FXML
    private Button addUser;
    @FXML
    private Button deleteUser;
    @FXML
    private Button editUser;
    @FXML
    private Button fillDB;

    @FXML
    private void AddUser(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
        Scene info_scene= new Scene(loader);
        Stage info_stage =new Stage();
        info_stage.setScene(info_scene);
        info_stage.initModality(Modality.APPLICATION_MODAL);
        info_stage.initOwner(addUser.getScene().getWindow());
        info_stage.showAndWait();
    }

    @FXML
    private void deleteUser(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("DeleteUser.fxml"));
        Scene info_scene= new Scene(loader);
        Stage info_stage =new Stage();
        info_stage.setScene(info_scene);
        info_stage.initModality(Modality.APPLICATION_MODAL);
        info_stage.initOwner(deleteUser.getScene().getWindow());
        info_stage.showAndWait();
    }

    @FXML
    private void editUser(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("EditUser.fxml"));
        Scene info_scene= new Scene(loader);
        Stage info_stage =new Stage();
        info_stage.setScene(info_scene);
        info_stage.initModality(Modality.APPLICATION_MODAL);
        info_stage.initOwner(editUser.getScene().getWindow());
        info_stage.showAndWait();
    }

    @FXML
    private void fillDB(ActionEvent event) throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
