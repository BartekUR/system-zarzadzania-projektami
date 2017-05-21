package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.sql.*;

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
    @FXML
    private Label labelWypelnijPola;
    @FXML
    private Label labelUzytkownikDodany;
    @FXML
    private Label labelRekordIstnieje;

    ObservableList <String> du_stanowiskoList = FXCollections.observableArrayList("Head","Pracownik");

    @FXML
    private void initialize(){
        du_stanowisko.setItems(du_stanowiskoList);
        //du_stanowisko.setValue("mm");
    }

    @FXML
    private void dodajUzytkownika(ActionEvent event) throws SQLException {
        String imie = du_imie.getText();
        String nazwisko = du_nazwisko.getText();
        Object stanowisko = du_stanowisko.getValue();
        int numberOfRows = 0;

        if (imie.length() == 0 || nazwisko .length() == 0 || stanowisko == null) {
            labelRekordIstnieje.setVisible(false);
            labelWypelnijPola.setVisible(true);
        } else {
            labelWypelnijPola.setVisible(false);
            try {
                String query_exists = "SELECT COUNT(*) AS total FROM `szp`.`pracownicy` WHERE `Imie`=(?) AND `Nazwisko`=(?) AND `Stanowisko`=(?)";
                PreparedStatement preparedStmte = conn.prepareStatement(query_exists);
                preparedStmte.setString(1, imie);
                preparedStmte.setString(2, nazwisko);
                preparedStmte.setObject(3, stanowisko);
                ResultSet rs = preparedStmte.executeQuery();
                while(rs.next())
                {
                    numberOfRows = rs.getInt("total");
                    //System.out.println(numberOfRows);
                }
                try {
                    if (numberOfRows == 0) {
                        String query = " INSERT INTO `szp`.`pracownicy` (`Login`, `Haslo`, `Imie`, `Nazwisko`, `Stanowisko`)"
                                + " VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement preparedStmt = conn.prepareStatement(query);
                        preparedStmt.setString(1, nazwisko + imie);
                        preparedStmt.setString(2, nazwisko);
                        preparedStmt.setString(3, imie);
                        preparedStmt.setString(4, nazwisko);
                        preparedStmt.setObject(5, stanowisko);
                        preparedStmt.executeUpdate();

                        labelRekordIstnieje.setVisible(false);
                        labelUzytkownikDodany.setVisible(true);
                        System.out.println("Rekord został wstawiony do tabeli pracownicy!");

                    } else if(numberOfRows >= 1){
                        labelUzytkownikDodany.setVisible(false);
                        labelRekordIstnieje.setVisible(true);
                        System.out.println("rekord juz istnieje");
                    }
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        //SzefGUIController.refresh();
        //SzefGUIController.parsePracownicy();
    }

    @FXML
    private void closeButtonAction(){

        Stage stage = (Stage) closeButton.getScene().getWindow();

        stage.close();
    }
}
