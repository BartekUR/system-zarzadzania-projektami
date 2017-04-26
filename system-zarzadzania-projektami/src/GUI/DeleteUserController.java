package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Michal on 2017-03-22.
 */
public class DeleteUserController {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();

    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private ChoiceBox uu_choice;
    @FXML
    private Button du_dodaj;


   /* private void wyswietl() throws SQLException {
        ObservableList<String> data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery(" SELECT (`Nazwisko` FROM `szp`.`pracownicy`;");

        while (rs.next()) {
            DataPracownicy uu = new DataPracownicy();
            uu.setPracownicyTable_nazwisko(rs.getString("Nazwisko"));
            data.add(uu);
        }
        //pracownicyTable.setItems(data);
        uu_choice.setItems(data);
    }*/

    /*@FXML
    private void initialize(){
        uu_choice.setItems(uu_choiceList);
        uu_choice.setValue("mm");
    }*/

    @FXML
    private void closeButtonAction(){

        Stage stage = (Stage) closeButton.getScene().getWindow();

        stage.close();
    }
}
