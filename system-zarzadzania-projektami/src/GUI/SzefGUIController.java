package GUI;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Michal on 2017-03-22.
 */
public class SzefGUIController implements Initializable {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();

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
    private void fillDB(ActionEvent event) throws SQLException, IOException {
/*        String line;

        BufferedReader br = new BufferedReader(new FileReader("db_test.sql"));
        System.out.println("Wypełnianie bazy testowymi danymi...");
        Statement stmt = conn.createStatement();
        while ((line = br.readLine()) != null) {
            if (line.length() != 0)
                stmt.executeUpdate(line);
        }
        conn.commit();
        parsePracownicy();
        parseProjekty();*/
    }

    @FXML private TableView<DataPracownicy> pracownicyTable;
    @FXML private TableColumn<DataPracownicy, Integer> pracownicyTable_id;
    @FXML private TableColumn<DataPracownicy, String> pracownicyTable_imie;
    @FXML private TableColumn<DataPracownicy, String> pracownicyTable_nazwisko;
    @FXML private TableColumn<DataPracownicy, String> pracownicyTable_stanowisko;

    @FXML private TableView<DataProjekty> projektyTable;
    @FXML private TableColumn<DataProjekty, Integer> projektyTable_id;
    @FXML private TableColumn<DataProjekty, String> projektyTable_nazwa;
    @FXML private TableColumn<DataProjekty, String> projektyTable_head;
    //@FXML private TableColumn<DataProjekty, String> projektyTable_pracownicy;
    @FXML private TableColumn<DataProjekty, String> projektyTable_status;
    @FXML private TableColumn<DataProjekty, String> projektyTable_progress;
    @FXML private TableColumn<DataProjekty, String> projektyTable_termin;

    @FXML private TextField nazwaProjektu;
    @FXML private TextField statusProjektu;
    @FXML private TextField progressProjektu;
    @FXML private TextField termin_koncowyProjektu;
    @FXML private TextField headProjektu;

    @FXML private ChoiceBox headChoice;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pracownicyTable_id.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_id"));
        pracownicyTable_imie.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_imie"));
        pracownicyTable_nazwisko.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_nazwisko"));
        pracownicyTable_stanowisko.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_stanowisko"));
        projektyTable_id.setCellValueFactory(new PropertyValueFactory<>("projektyTable_id"));
        projektyTable_nazwa.setCellValueFactory(new PropertyValueFactory<>("projektyTable_nazwa"));
        projektyTable_head.setCellValueFactory(new PropertyValueFactory<>("projektyTable_head"));
        //projektyTable_pracownicy.setCellValueFactory(new PropertyValueFactory<>("projektyTable_pracownicy"));
        projektyTable_status.setCellValueFactory(new PropertyValueFactory<>("projektyTable_status"));
        projektyTable_progress.setCellValueFactory(new PropertyValueFactory<>("projektyTable_progress"));
        projektyTable_termin.setCellValueFactory(new PropertyValueFactory<>("projektyTable_termin"));
        try {
            parsePracownicy();
            parseProjekty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void parsePracownicy() throws SQLException {
        ObservableList<DataPracownicy> data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `szp`.`pracownicy`;");
        while (rs.next()) {
            DataPracownicy dp = new DataPracownicy();
            dp.setPracownicyTable_id(rs.getInt("ID_Pracownik"));
            dp.setPracownicyTable_imie(rs.getString("Imie"));
            dp.setPracownicyTable_nazwisko(rs.getString("Nazwisko"));
            dp.setPracownicyTable_stanowisko(rs.getString("Stanowisko"));
            data.add(dp);
        }
        pracownicyTable.setItems(data);
    }

    private void parseProjekty() throws SQLException {
        ObservableList<DataProjekty> data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `szp`.`projekty`;");
        while (rs.next()) {
            DataProjekty dp = new DataProjekty();
            dp.setProjektyTable_id(rs.getInt("ID_Projekt"));
            dp.setProjektyTable_nazwa(rs.getString("Nazwa_projektu"));
            dp.setProjektyTable_head(rs.getString("Head"));
            dp.setProjektyTable_status(rs.getString("Status"));
            dp.setProjektyTable_progress(rs.getString("Progress"));
            dp.setProjektyTable_termin(rs.getString("Termin"));

            data.add(dp);
        }
        projektyTable.setItems(data);
    }

    @FXML
    private void addProjekt(ActionEvent event) throws SQLException  {
        String name = nazwaProjektu.getText();
        String status = statusProjektu.getText();
        String progress = progressProjektu.getText();
        String termin = termin_koncowyProjektu.getText();
        String head = headProjektu.getText();

        //String head_c = headChoice.getAccessibleText();

        try {

            String query = " insert into `szp`.`projekty` (`Nazwa_projektu`, `Head`, `Status`, `Progress`, `Termin`)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, head);
            preparedStmt.setString(3, status);
            preparedStmt.setString(4, progress);
            preparedStmt.setString(5, termin);

            preparedStmt.executeUpdate();

            System.out.println("Rekord został wstawiony do tabeli projekty!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        //ResultSet rs = conn.createStatement().executeQuery("SELECT `Nazwisko` FROM `szp`.`pracownicy` where `Stanowisko`='Head';");

    }


    public TextField getNazwaProjektu() {
        return nazwaProjektu;
    }

    public void setNazwaProjektu(TextField nazwaProjektu) {
        this.nazwaProjektu = nazwaProjektu;
    }
}
