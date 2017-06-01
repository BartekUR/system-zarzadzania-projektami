package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

public class EditUserController implements Initializable {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();

    public ChoiceBox euStanowisko;
    public TextField euImie, euNazwisko, euNoweHaslo;
    @FXML private javafx.scene.control.Button closeButton;
    @FXML private ComboBox comboBoxWyborUzytkownika;
    @FXML private Label labelEditUser;

    @FXML
    private void closeButtonAction(){

        Stage stage = (Stage) closeButton.getScene().getWindow();

        stage.close();
    }

    @FXML
    private void fillcomboBoxDU() throws SQLException {
        final ObservableList<Integer> options = FXCollections.observableArrayList();
        String query = "SELECT `ID_Pracownik` FROM `szp`.`pracownicy`;";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()){

            options.add(rs.getInt("ID_Pracownik"));
        }
        comboBoxWyborUzytkownika.setItems(options);

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

        ObservableList <String> euStanowiskoList = FXCollections.observableArrayList("Head","Pracownik","Szef");
        euStanowisko.setItems(euStanowiskoList);

        try {
            fillcomboBoxDU();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editButtonAction() throws SQLException {
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
}
