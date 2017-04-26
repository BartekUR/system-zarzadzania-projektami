package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Michal on 2017-03-22.
 */
public class AddUserController {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();

    @FXML
    private Button closeButton;

    @FXML
    private TextField du_imie;
    @FXML
    private TextField du_nazwisko;
    @FXML
    private ChoiceBox du_stanowisko;
    @FXML
    private Button du_dodaj;

    ObservableList <String> du_stanowiskoList = FXCollections.observableArrayList("Head","Pracownik");

    @FXML
    private void initialize(){
        du_stanowisko.setItems(du_stanowiskoList);
        du_stanowisko.setValue("mm");
    }

    @FXML
    private void dodajUzytkownika(ActionEvent event) throws SQLException {
        String imie = du_imie.getText();
        String nazwisko = du_nazwisko.getText();
        Object stanowisko=du_stanowisko.getValue();

        try {

            String query = " insert into `szp`.`pracownicy` (`Login`, `Haslo`, `Imie`, `Nazwisko`, `Stanowisko`)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, nazwisko + imie);
            preparedStmt.setString(2, nazwisko);
            preparedStmt.setString(3, imie);
            preparedStmt.setString(4, nazwisko);
            preparedStmt.setObject(5, stanowisko);

            preparedStmt.executeUpdate();

            System.out.println("Rekord został wstawiony do tabeli projekty!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void closeButtonAction(){

        Stage stage = (Stage) closeButton.getScene().getWindow();

        stage.close();
    }
}
