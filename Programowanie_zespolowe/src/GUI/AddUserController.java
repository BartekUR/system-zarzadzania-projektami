package GUI;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by Michal on 2017-03-22.
 */
public class AddUserController {
    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private void closeButtonAction(){

        Stage stage = (Stage) closeButton.getScene().getWindow();

        stage.close();
    }
}
