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

public class HeadGUIController implements Initializable  {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();

    @FXML private TableView<DataPracownicy> pracownikTable, pracownicyInProject_Table;
    @FXML private TableColumn<DataPracownicy, Integer> pracownikTable_id, pracownicyInProject_Table_id;
    @FXML private TableColumn<DataPracownicy, String> pracownikTable_imie,  pracownicyInProject_Table_imie;
    @FXML private TableColumn<DataPracownicy, String> pracownikTable_nazwisko, pracownicyInProject_Table_nazwisko;

    @FXML private TableView<DataTaski> mojeProjekty;
    @FXML private TableColumn<DataTaski, Integer> idMProjekty;
    @FXML private TableColumn<DataTaski, String> taskMProjekty, pracownikMProjekty, statusMProjekty, terminMProjekty;

    @FXML private TableView<DataProjekty> projectsOfTheOnlineHead;
    @FXML private TableColumn<DataProjekty, Integer> idProjectHead;
    @FXML private TableColumn<DataProjekty, String> nazwaProjektHead;

    @FXML private TableView<DataTaski> taskiTable, taskiTable1;
    @FXML private TableColumn<DataTaski, Integer> taskiTable_id, taskiTable_id1;
    @FXML private TableColumn<DataTaski, String> taskiTable_nazwa, taskiTable_nazwa1;

    @FXML private ComboBox comboBoxProjects, comboBoxHead, comboBoxSelectProject2, comboBoxSelectPracownik;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idMProjekty.setCellValueFactory(new PropertyValueFactory<>("taskiTable_id"));
        taskMProjekty.setCellValueFactory(new PropertyValueFactory<>("taskiTable_nazwa"));
        pracownikMProjekty.setCellValueFactory(new PropertyValueFactory<>("taskiTable_pracownik"));
        statusMProjekty.setCellValueFactory(new PropertyValueFactory<>("taskiTable_status"));
        terminMProjekty.setCellValueFactory(new PropertyValueFactory<>("taskiTable_termin"));

        pracownikTable_id.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_id"));
        pracownikTable_imie.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_imie"));
        pracownikTable_nazwisko.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_nazwisko"));

        idProjectHead.setCellValueFactory(new PropertyValueFactory<>("projektyTable_id"));
        nazwaProjektHead.setCellValueFactory(new PropertyValueFactory<>("projektyTable_nazwa"));

        pracownicyInProject_Table_id.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_id"));
        pracownicyInProject_Table_imie.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_imie"));
        pracownicyInProject_Table_nazwisko.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_nazwisko"));

        taskiTable_id.setCellValueFactory(new PropertyValueFactory<>("taskiTable_id"));
        taskiTable_nazwa.setCellValueFactory(new PropertyValueFactory<>("taskiTable_nazwa"));

        taskiTable_id1.setCellValueFactory(new PropertyValueFactory<>("taskiTable_id"));
        taskiTable_nazwa1.setCellValueFactory(new PropertyValueFactory<>("taskiTable_nazwa"));

        System.out.println("Zainicjalizowano kontroler Heada dla: " + who);

        try {
            wyswietlProjektyHeadaCombo();
            wyswietlProjektyHeadaTable();
            wyswietlPracownikowTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void wyswietlProjektyHeadaTable() throws SQLException {

        ObservableList<DataProjekty> headProjects  = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `szp`.`projekty` WHERE `Head`='" + who + "'");

        while (rs.next()) {
            DataProjekty hp = new DataProjekty();
            hp.setProjektyTable_id(rs.getInt("ID_Projekt"));
            hp.setProjektyTable_nazwa(rs.getString("Nazwa_projektu"));
            headProjects.add(hp);
        }

        projectsOfTheOnlineHead.setItems(headProjects);
        projectsOfTheOnlineHead.refresh();
    }

    @FXML
    private void wyswietlPracownikowTable() throws SQLException {

        ObservableList<DataPracownicy> employees = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `szp`.`pracownicy` where `Stanowisko`='Pracownik';");

        while (rs.next()) {
            DataPracownicy emp = new DataPracownicy();
            emp.setPracownicyTable_id(rs.getInt("ID_Pracownik"));
            emp.setPracownicyTable_imie(rs.getString("Imie"));
            emp.setPracownicyTable_nazwisko(rs.getString("Nazwisko"));
            employees.add(emp);
        }

        pracownikTable.setItems(employees);
        pracownikTable.refresh();
    }

    @FXML
    private void wyswietlPracownikowProjektu(ActionEvent event) throws SQLException {

        DataProjekty projectHead =  projectsOfTheOnlineHead.getSelectionModel().getSelectedItem();

        if(projectHead != null) {

            String name_project = projectHead.getProjektyTable_nazwa();
            ObservableList<DataPracownicy> employeesInProject = FXCollections.observableArrayList();

            try {
                String query = ("SELECT pra.ID_pracownik, pra.Imie, pra.Nazwisko \n" +
                        "FROM szp.pracownicy pra INNER JOIN szp.pracownicy_i_projekty pip INNER JOIN szp.projekty pro\n" +
                        "WHERE pra.ID_Pracownik=pip.ID_Pracownik_FK\n" +
                        "AND pro.ID_Projekt=pip.ID_Projekt_FK\n" +
                        "AND pro.Nazwa_projektu=(?)");
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, name_project);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    DataPracownicy ep = new DataPracownicy();
                    ep.setPracownicyTable_id(rs.getInt("ID_Pracownik"));
                    ep.setPracownicyTable_imie(rs.getString("Imie"));
                    ep.setPracownicyTable_nazwisko(rs.getString("Nazwisko"));
                    employeesInProject.add(ep);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            pracownicyInProject_Table.setItems(employeesInProject);
        }

    }

    @FXML
    private void dodajPracownikaDoProjektu(ActionEvent e) throws SQLException{
        DataPracownicy person = pracownikTable.getSelectionModel().getSelectedItem();//obiekt DataPracownicy zaznaczonego wiersza
        DataProjekty project =  projectsOfTheOnlineHead.getSelectionModel().getSelectedItem();

        if(person!=null && project!=null){
            String id_person = person.getPracownicyTable_id().toString();
            String id_project = project.getProjektyTable_id().toString();
            try {
                String query = " insert into `szp`.`pracownicy_i_projekty` (`ID_Pracownik_FK`, `ID_Projekt_FK`) values (?, ?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, id_person);
                pst.setString(2, id_project);
                pst.executeUpdate();

                System.out.println("Rekord został wstawiony do tabeli projekty!");
            } catch (SQLException d){
                System.out.println(d.getMessage());
            }
            wyswietlPracownikowProjektu(e);
        }
    }

    @FXML
    private void usunPracownikaZProjektu(ActionEvent event) throws SQLException {
        DataPracownicy personDelete = pracownicyInProject_Table.getSelectionModel().getSelectedItem();//obiekt DataPracownicy zaznaczonego wiersza
        DataProjekty projectDel =  projectsOfTheOnlineHead.getSelectionModel().getSelectedItem();

        if(personDelete != null && projectDel != null) {
            String id_pracownik = personDelete.getPracownicyTable_id().toString();
            String id_projekt = projectDel.getProjektyTable_id().toString();
            try {
                String query = "DELETE FROM szp.pracownicy_i_projekty WHERE ID_Pracownik_FK=(?) AND ID_Projekt_FK=(?);";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, id_pracownik);
                pst.setString(2, id_projekt);

                pst.executeUpdate();
                System.out.println("Pracownik został usunięty z projektu!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            wyswietlPracownikowProjektu(event);
        }

    }

    @FXML
    private void wyswietlTaskiProjektuTable3(ActionEvent event) throws SQLException {

        ObservableList<DataTaski> data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT t.ID_Task, t.Nazwa_tasku " +
                "FROM szp.pracownicy pra, szp.pracownicy_i_taski pit, szp.taski t, szp.projekty pro " +
                "WHERE pra.ID_Pracownik=pit.ID_Pracownik_FK " +
                "AND t.ID_Task=pit.ID_Taski_FK AND pro.ID_Projekt=t.ID_Projekt_FK AND pro.Nazwa_projektu='" +
                comboBoxProjects.getValue().toString() + "';");

        while (rs.next()) {
            DataTaski dt = new DataTaski();
            dt.setTaskiTable_id(rs.getInt("ID_Task"));
            dt.setTaskiTable_nazwa(rs.getString("Nazwa_tasku"));
            data.add(dt);
        }

        taskiTable1.setItems(data);
        taskiTable1.refresh();
    }

    @FXML
    private void wyswietlTaskiProjektuTable2(ActionEvent event) throws SQLException {

        ObservableList<DataTaski> data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT t.ID_Task, t.Nazwa_tasku " +
                "FROM szp.pracownicy pra, szp.pracownicy_i_taski pit, szp.taski t, szp.projekty pro " +
                "WHERE pra.ID_Pracownik=pit.ID_Pracownik_FK " +
                "AND t.ID_Task=pit.ID_Taski_FK AND pro.ID_Projekt=t.ID_Projekt_FK AND pro.Nazwa_projektu='" +
                comboBoxSelectProject2.getValue().toString() + "';");

        while (rs.next()) {
            DataTaski dt = new DataTaski();
            dt.setTaskiTable_id(rs.getInt("ID_Task"));
            dt.setTaskiTable_nazwa(rs.getString("Nazwa_tasku"));
            data.add(dt);
        }

        taskiTable.setItems(data);
        taskiTable.refresh();
        wyswietlPracownikowCombo();
    }

    @FXML
    private void wyswietlTaskiProjektuTable1(ActionEvent event) throws SQLException  {

        ObservableList<DataTaski> data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT t.ID_Projekt_FK, t.Nazwa_tasku, pra.Nazwisko, pra.Imie,t.Status, t.Termin, t.ID_Task " +
                        "FROM szp.pracownicy pra, szp.pracownicy_i_taski pit, szp.taski t, szp.projekty pro " +
                        "WHERE pra.ID_Pracownik=pit.ID_Pracownik_FK " +
                        "AND t.ID_Task=pit.ID_Taski_FK " +
                        "AND pro.ID_Projekt=t.ID_Projekt_FK " +
                        "AND pro.Nazwa_projektu='" + comboBoxHead.getValue().toString() + "';");

        while (rs.next()) {
            DataTaski dt = new DataTaski();
            dt.setTaskiTable_id(rs.getInt("t.ID_Task"));
            dt.setTaskiTable_nazwa(rs.getString("t.Nazwa_tasku"));
            dt.setTaskiTable_pracownik(rs.getString("pra.Nazwisko") + " " + rs.getString("pra.Imie"));
            dt.setTaskiTable_status(rs.getString("t.Status"));
            dt.setTaskiTable_termin(rs.getString("t.Termin"));
            data.add(dt);
        }

        mojeProjekty.setItems(data);
        mojeProjekty.refresh();
    }

    @FXML
    public void wyswietlProjektyHeadaCombo() throws SQLException {

        ObservableList<String> options = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM `szp`.`projekty` WHERE `Head`='" + who + "'";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("Nazwa_projektu"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        comboBoxProjects.setItems(options);
        comboBoxHead.setItems(options);
        comboBoxSelectProject2.setItems(options);
    }

    @FXML
    public void wyswietlPracownikowCombo() throws SQLException {

        ObservableList<String> options = FXCollections.observableArrayList();

        try {
            String query = ("SELECT pra.ID_pracownik, pra.Imie, pra.Nazwisko \n" +
                    "FROM szp.pracownicy pra INNER JOIN szp.pracownicy_i_projekty pip INNER JOIN szp.projekty pro\n" +
                    "WHERE pra.ID_Pracownik=pip.ID_Pracownik_FK\n" +
                    "AND pro.ID_Projekt=pip.ID_Projekt_FK\n" +
                    "AND pro.Nazwa_projektu='" + comboBoxSelectProject2.getValue().toString() + "';");
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("Imie") + " " + rs.getString("Nazwisko"));
            }
            comboBoxSelectPracownik.setItems(options);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void usunTask(ActionEvent event) throws SQLException {
        DataTaski taskDelete = taskiTable.getSelectionModel().getSelectedItem();

        if (taskDelete != null) {
            String id_task = taskDelete.getTaskiTable_id().toString();
            try {
                String query = "DELETE FROM szp.taski WHERE ID_Task=" + id_task + ";";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.executeUpdate();
                System.out.println("Task został usunięty z projektu!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            wyswietlTaskiProjektuTable2(event);
        }
    }

     public void addTask(ActionEvent actionEvent) {
        
    } 
}
