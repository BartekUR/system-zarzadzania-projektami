package GUI;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ResourceBundle;

import static GUI.LogowanieController.who;

/**
 * Created by Michal on 2017-03-22.
 */
public class HeadGUIController implements Initializable  {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();

    //@FXML private Button button;
    @FXML private TableView<DataPracownicy> pracownikTable, pracownicyInProject_Table;
    @FXML private TableColumn<DataPracownicy, Integer> pracownikTable_id, pracownicyInProject_Table_id;
    @FXML private TableColumn<DataPracownicy, String> pracownikTable_imie,  pracownicyInProject_Table_imie;
    @FXML private TableColumn<DataPracownicy, String> pracownikTable_nazwisko, pracownicyInProject_Table_nazwisko;
    @FXML private Button addButton;
    @FXML private ComboBox comboBoxHead;

    @FXML private TableView<DataMojeProjekty> mojeProjekty;
    @FXML private TableColumn<DataMojeProjekty, Integer> idMProjekty;
    @FXML private TableColumn<DataMojeProjekty, String> taskMProjekty;
    @FXML private TableColumn<DataMojeProjekty, String> pracownikMProjekty;
    @FXML private TableColumn<DataMojeProjekty, String> progressMProjekty;
    @FXML private TableColumn<DataMojeProjekty, String> terminMProjekty;

    @FXML private TableView<DataProjekty> projectsOfTheOnlineHead;
    @FXML private TableColumn<DataProjekty, Integer> idProjectHead;
    @FXML private TableColumn<DataProjekty, String> nazwaProjektHead;

    @FXML private Button pokazProjekty;

    @FXML private ComboBox comboBoxSelectProject2;
    @FXML private ComboBox comboBoxSelectPracownik;
    @FXML private TableView<DataTaski> taskiTable;
    @FXML private TableColumn<DataTaski, Integer> taskiTable_id;
    @FXML private TableColumn<DataTaski, String> taskiTable_nazwa;
    @FXML private TableView<DataTaski> taskiPracownik;
    @FXML private TableColumn<DataTaski, Integer> taskiPracownik_id;
    @FXML private TableColumn<DataTaski, String> taskiPracownik_nazwa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fillcomboBox2();
            displayEmployeesStatusPracownik();
            displayHeadsProjects();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idMProjekty.setCellValueFactory(new PropertyValueFactory<>("idMProjekty"));
        taskMProjekty.setCellValueFactory(new PropertyValueFactory<>("taskMProjekty"));
        pracownikMProjekty.setCellValueFactory(new PropertyValueFactory<>("pracownikMProjekty"));
        progressMProjekty.setCellValueFactory(new PropertyValueFactory<>("progressMProjekty"));
        terminMProjekty.setCellValueFactory(new PropertyValueFactory<>("terminMProjekty"));

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

        System.out.println("Zainicjalizowano kontroler Heada dla: " + who);
    }

    @FXML
    private void displayHeadsProjects() throws SQLException {
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
    private void displayEmployeesStatusPracownik() throws SQLException {
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
    private void refreshEmployees(ActionEvent event) throws SQLException {
        displayEmployeesStatusPracownik();
    }

    @FXML
    private void displayEmployeesInProject(ActionEvent event) throws SQLException {
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
    private void addEmployeeToProject (ActionEvent e) throws SQLException{
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
            displayEmployeesInProject(e);
        }
    }

    @FXML
    private void deleteUserInProject(ActionEvent event) throws IOException,SQLException {
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
            displayEmployeesInProject(event);
        }

    }

    @FXML
    private void pokazProjekt(ActionEvent event) throws SQLException  {
        String projekt = comboBoxHead.getValue().toString();
        ObservableList<DataMojeProjekty> data = FXCollections.observableArrayList();
        String query = ("SELECT t.ID_Projekt_FK, t.Nazwa_tasku, pra.Nazwisko, t.Progress, t.Termin\n" +
                    "FROM szp.pracownicy pra, szp.pracownicy_i_taski pit, szp.taski t, szp.projekty pro\n" +
                    "WHERE pra.ID_Pracownik=pit.ID_Pracownik_FK\n" +
                    "AND t.ID_Task=pit.ID_Taski_FK\n" +
                    "AND pro.ID_Projekt=t.ID_Projekt_FK\n" +
                    "AND pro.Nazwa_projektu=(?)");

        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, projekt);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            DataMojeProjekty dp = new DataMojeProjekty();
            dp.setidMProjekty(rs.getInt("t.ID_Projekt_FK"));
            dp.settaskMProjekty(rs.getString("t.Nazwa_tasku"));
            dp.setpracownikMProjekty(rs.getString("pra.Nazwisko"));
            dp.setprogressMProjekty(rs.getString("t.Progress"));
            dp.setterminMProjekty(rs.getString("t.Termin"));
            data.add(dp);
        }
        mojeProjekty.setItems(data);
    }
    
    @FXML
    public void fillcomboBox2() throws SQLException {
        ObservableList<String> options = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM `szp`.`projekty` WHERE `Head`='" + who + "'";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("Nazwa_projektu"));
            }
            comboBoxHead.setItems(options);
            comboBoxSelectProject2.setItems(options);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void fillcomboBox3() throws SQLException {
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
    private void parseTasks() throws SQLException {
        ObservableList<DataTaski> data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT t.ID_Task, t.Nazwa_tasku\n" +
                "FROM szp.pracownicy pra, szp.pracownicy_i_taski pit, szp.taski t, szp.projekty pro\n" +
                "WHERE pra.ID_Pracownik=pit.ID_Pracownik_FK\n" +
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
        fillcomboBox3();
    }

}
