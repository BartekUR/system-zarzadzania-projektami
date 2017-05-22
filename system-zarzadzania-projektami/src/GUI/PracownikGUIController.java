package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static GUI.LogowanieController.who;
import static GUI.LogowanieController.whoLogin;

/**
 * Created by Przemek on 21.05.2017.
 */
public class PracownikGUIController implements Initializable {
    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();

    @FXML private TableView<DataProjekty> tableProjektPracownika;
    @FXML private TableColumn<DataProjekty, String> columnProjekt;
    @FXML private TableColumn<DataProjekty, String> columnHead;
    @FXML private TableColumn<DataProjekty, String> columnStatus;
    @FXML private TableColumn<DataProjekty, String> columnTermin;

    @FXML private ComboBox comboBoxProjektPracownika;
    @FXML private Button buttonZatwierdzProjekt;
    @FXML private TableView<DataTaski> tableTaskiProjektu;
    @FXML private TableColumn<DataTaski, String> columnIdTask;
    @FXML private TableColumn<DataTaski, String> columnNazwaTasku;
    @FXML private TableColumn<DataTaski, String> columnStatusTasku;
    @FXML private TableColumn<DataTaski, String> columnTerminTasku;
    @FXML private ComboBox comboBoxIdTask;
    @FXML private ComboBox comboBoxStatusTasku;
    @FXML private Button buttonUpdateStatus;
    @FXML private Label labelZmianaStatusu;

    ObservableList <String> statusTaskuList = FXCollections.observableArrayList("Rozpoczęty","W trakcie","Opóźniony", "Zakończony" );


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
            wyswietlProjektyPracownika();
            fillcomboBoxProjektyPracownika();
            fillcomboBoxIdTasku();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        comboBoxStatusTasku.setItems(statusTaskuList);
    }

    @FXML
    private void wyswietlProjektyPracownika() throws SQLException {
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
            //System.out.println(dataProjekty2);
        }

        tableProjektPracownika.setItems(dataProjekty2);
        tableProjektPracownika.refresh();
    }

    @FXML
    private void fillcomboBoxProjektyPracownika() throws SQLException {
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
    private void wyswietlTaskiProjektu(ActionEvent event) throws SQLException {
        String projekt = comboBoxProjektPracownika.getValue().toString();
        ObservableList<DataTaski> data = FXCollections.observableArrayList();
        if(projekt != null) { //nie działa
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
                tableTaskiProjektu.setItems(data);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void fillcomboBoxIdTasku() throws SQLException {
        ObservableList<String> idTaskuList = FXCollections.observableArrayList();
       // String projekt2 = comboBoxProjektPracownika.getValue().toString();
        //if(projekt2 != null) {
            try {
                String query = "SELECT t.ID_Task \n" +
                        "FROM szp.taski t, szp.pracownicy pra, szp.pracownicy_i_taski pit, szp.projekty pro\n" +
                        "WHERE pra.ID_Pracownik = pit.ID_Pracownik_FK\n" +
                        "AND t.ID_Task=pit.ID_Taski_FK\n" +
                        "AND pro.ID_Projekt=t.ID_Projekt_FK\n" +
                        "AND pra.`Login`=(?)";
                       // "AND pro.Nazwa_projektu=(?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, whoLogin);
                //pst.setString(2, projekt2);

                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    idTaskuList.add(rs.getString("ID_Task"));
                }
                comboBoxIdTask.setItems(idTaskuList);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        //}
    }

    @FXML
    private void editStatusTasku(ActionEvent actionEvent) throws SQLException {
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
        wyswietlTaskiProjektu(actionEvent);
    }




}