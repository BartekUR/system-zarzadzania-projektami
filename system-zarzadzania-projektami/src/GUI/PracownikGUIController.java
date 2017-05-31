package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

import static GUI.LogowanieController.who;
import static GUI.LogowanieController.whoLogin;

public class PracownikGUIController implements Initializable {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();

    @FXML private TableView<DataProjekty> tableProjektPracownika;
    @FXML private TableColumn<DataProjekty, String> columnProjekt, columnHead, columnStatus, columnTermin;

    @FXML private TableView<DataTaski> tableTaskiProjektu;
    @FXML private TableColumn<DataTaski, String> columnIdTask, columnNazwaTasku, columnStatusTasku, columnTerminTasku;

    @FXML private ComboBox comboBoxIdTask, comboBoxStatusTasku, comboBoxProjektPracownika;

    @FXML private Label labelZmianaStatusu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Zainicjalizowano kontroler Pracownika dla: " + who);

        columnProjekt.setCellValueFactory(new PropertyValueFactory<>("projektyTable_nazwa"));
        columnHead.setCellValueFactory(new PropertyValueFactory<>("projektyTable_head"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("projektyTable_status"));
        columnTermin.setCellValueFactory(new PropertyValueFactory<>("projektyTable_termin"));

        columnIdTask.setCellValueFactory(new PropertyValueFactory<>("taskiTable_id"));
        columnNazwaTasku.setCellValueFactory(new PropertyValueFactory<>("taskiTable_nazwa"));
        columnStatusTasku.setCellValueFactory(new PropertyValueFactory<>("taskiTable_status"));
        columnTerminTasku.setCellValueFactory(new PropertyValueFactory<>("taskiTable_termin"));

        try {
            wyswietlProjektyPracownikaTable();
            wyswietlProjektyPracownikaCombo();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList <String> statusTaskuList = FXCollections.observableArrayList("Rozpoczęty", "W trakcie", "Opóźniony", "Zakończony");
        comboBoxStatusTasku.setItems(statusTaskuList);
    }

    @FXML
    private void wyswietlProjektyPracownikaTable() throws SQLException {

        ObservableList<DataProjekty> dataProjekty2 = FXCollections.observableArrayList();

        String query = "SELECT pro.`Nazwa_projektu`, pro.`Head`, pro.`Status`, pro.`Termin`\n" +
                "FROM szp.projekty pro , szp.pracownicy_i_projekty pip, szp.pracownicy pra\n" +
                "WHERE pro.ID_Projekt = pip.ID_Projekt_FK\n" +
                "AND pra.ID_Pracownik = pip.ID_Pracownik_FK\n" +
                "AND pra.Login =(?);";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, whoLogin);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            DataProjekty dp = new DataProjekty();
            dp.setProjektyTable_nazwa(rs.getString("Nazwa_projektu"));
            dp.setProjektyTable_head(rs.getString("Head"));
            dp.setProjektyTable_status(rs.getString("Status"));
            dp.setProjektyTable_termin(rs.getString("Termin"));
            dataProjekty2.add(dp);
        }

        tableProjektPracownika.setItems(dataProjekty2);
        tableProjektPracownika.refresh();
    }

    @FXML
    private void wyswietlProjektyPracownikaCombo() throws SQLException {

        ObservableList<String> options = FXCollections.observableArrayList();

        try {
            String query = "SELECT Nazwa_projektu " +
                    "FROM szp.projekty pro, szp.pracownicy pra, szp.pracownicy_i_projekty pip " +
                    "WHERE pro.ID_Projekt = pip.ID_Projekt_FK " +
                    "AND pra.ID_Pracownik = pip.ID_Pracownik_FK " +
                    "AND pra.Login =(?);";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, whoLogin);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("Nazwa_projektu"));
            }
            comboBoxProjektPracownika.setItems(options);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void wyswietlTaskiPracownikaProjektuTable(ActionEvent event) throws SQLException {

        String projekt = comboBoxProjektPracownika.getValue().toString();
        ObservableList<DataTaski> data = FXCollections.observableArrayList();

        try {
            String query = "SELECT t.ID_Task, t.Nazwa_tasku, t.`Status`, t.Termin " +
                    "FROM szp.pracownicy pra, szp.pracownicy_i_taski pit, szp.taski t, szp.projekty pro " +
                    "WHERE pra.ID_Pracownik=pit.ID_Pracownik_FK " +
                    "AND t.ID_Task=pit.ID_Taski_FK " +
                    "AND pro.ID_Projekt=t.ID_Projekt_FK " +
                    "AND pro.Nazwa_projektu=(?)" +
                    "AND pra.Login =(?);";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, projekt);
            pst.setString(2, whoLogin);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DataTaski dt = new DataTaski();
                dt.setTaskiTable_id(rs.getInt("t.ID_Task"));
                dt.setTaskiTable_nazwa(rs.getString("t.Nazwa_tasku"));
                dt.setTaskiTable_status(rs.getString("t.Status"));
                dt.setTaskiTable_termin(rs.getString("t.Termin"));
                data.add(dt);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        tableTaskiProjektu.setItems(data);
        wyswietlTaskiPracownikaProjektuCombo();
    }

    @FXML
    private void wyswietlTaskiPracownikaProjektuCombo() throws SQLException {
        String projekt = comboBoxProjektPracownika.getValue().toString();

        ObservableList<Integer> idTaskuList = FXCollections.observableArrayList();

        try {
            String query = "SELECT t.ID_Task " +
                    "FROM szp.taski t, szp.pracownicy pra, szp.pracownicy_i_taski pit, szp.projekty pro " +
                    "WHERE pra.ID_Pracownik = pit.ID_Pracownik_FK " +
                    "AND t.ID_Task=pit.ID_Taski_FK " +
                    "AND pro.ID_Projekt=t.ID_Projekt_FK " +
                    "AND pro.Nazwa_projektu=(?) " +
                    "AND pra.`Login`=(?);";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, projekt);
            pst.setString(2, whoLogin);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                idTaskuList.add(rs.getInt("ID_Task"));
            }
            comboBoxIdTask.setItems(idTaskuList);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void zmienStatusTasku(ActionEvent actionEvent) throws SQLException {

        String status = comboBoxStatusTasku.getValue().toString();
        String id_tasku = comboBoxIdTask.getValue().toString();

        try {
            String query = " UPDATE `szp`.`taski` SET `Status` = ? WHERE `ID_Task` = ? ";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, status);
            preparedStmt.setString(2, id_tasku);

            preparedStmt.executeUpdate();

            labelZmianaStatusu.setVisible(true);
            System.out.println("Rekord "+id_tasku+" został edytowany!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        wyswietlTaskiPracownikaProjektuTable(actionEvent);
    }
}