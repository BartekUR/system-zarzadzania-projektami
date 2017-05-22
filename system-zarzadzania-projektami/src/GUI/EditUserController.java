package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Michal on 2017-03-22.
 */
public class EditUserController implements Initializable {
    public TextField euImie;
    public TextField euNazwisko;
    public ChoiceBox euStanowisko;
    public TextField euNoweHaslo;
    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();
    @FXML private javafx.scene.control.Button closeButton;
    @FXML private ComboBox comboBoxWyborUzytkownika;
    @FXML private Label labelEditUser;
    @FXML
    private void closeButtonAction(){

        Stage stage = (Stage) closeButton.getScene().getWindow();

        stage.close();
    }
    @FXML
    private void fillcomboBoxDU() throws SQLException, IOException {
        final ObservableList<Integer> options = FXCollections.observableArrayList();
        String query = "SELECT `ID_Pracownik` FROM `szp`.`pracownicy`;";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()){

            options.add(rs.getInt("ID_Pracownik"));
        }
        comboBoxWyborUzytkownika.setItems(options);
        //conn.close();
        //rs.close();

        if (comboBoxWyborUzytkownika.getValue() != null){
            String id = comboBoxWyborUzytkownika.getValue().toString(); 
            rs = conn.createStatement().executeQuery("SELECT * FROM `szp`.`pracownicy` where `ID_Pracownik`='"+id+"';");

            while(rs.next()) {
                String imie = rs.getString("Imie");
                String nazwisko = rs.getString("Nazwisko");
                String stanowisko = rs.getString("Stanowisko");
                String haslo = rs.getString("Haslo");
                euImie.setText(imie);
                euNazwisko.setText(nazwisko);
                euStanowisko.setValue(stanowisko);
                euNoweHaslo.setText(haslo);
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        euStanowisko.setItems(euStanowiskoList);
        //euStanowisko.setValue("mm");
        try {
            fillcomboBoxDU();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editButtonAction(ActionEvent actionEvent) throws SQLException {
        Object id = comboBoxWyborUzytkownika.getValue().toString();
        String imie = euImie.getText();
        String nazwisko = euNazwisko.getText();
        Object stanowisko = euStanowisko.getValue();
        String haslo = euNoweHaslo.getText();
        
        try {

            String query = " UPDATE `szp`.`pracownicy` set `Login` = ?, `Haslo` = ?, `Imie` = ?, `Nazwisko` = ?, `Stanowisko` = ? where `ID_Pracownik`='"+id+"'; ";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, nazwisko + imie);
            preparedStmt.setString(2, haslo);
            preparedStmt.setString(3, imie);
            preparedStmt.setString(4, nazwisko);
            preparedStmt.setObject(5, stanowisko);

            preparedStmt.executeUpdate();

            labelEditUser.setVisible(true);
            System.out.println("Rekord "+id+" został edytowany!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }


    }
    ObservableList <String> euStanowiskoList = FXCollections.observableArrayList("Head","Pracownik","Szef");
}
