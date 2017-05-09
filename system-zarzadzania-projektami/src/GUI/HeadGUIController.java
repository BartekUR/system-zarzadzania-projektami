package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ResourceBundle;

/**
 * Created by Michal on 2017-03-22.
 */
public class HeadGUIController implements Initializable  {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();

    @FXML private ComboBox comboBoxSelectProject = new ComboBox();
    //@FXML private Button button;
    @FXML private TableView<DataPracownicy> pracownicyTable, pracownicyInProject_Table;
    @FXML private TableColumn<DataPracownicy, Integer> pracownicyTable_id, pracownicyInProject_Table_id;
    @FXML private TableColumn<DataPracownicy, String> pracownicyTable_imie,  pracownicyInProject_Table_imie;
    @FXML private TableColumn<DataPracownicy, String> pracownicyTable_nazwisko, pracownicyInProject_Table_nazwisko;
    @FXML private Button addButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fillComboBoxSelectProject();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pracownicyTable_id.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_id"));
        pracownicyTable_imie.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_imie"));
        pracownicyTable_nazwisko.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_nazwisko"));

        pracownicyInProject_Table_id.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_id"));
        pracownicyInProject_Table_imie.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_imie"));
        pracownicyInProject_Table_nazwisko.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_nazwisko"));
    }

    @FXML
    private void fillComboBoxSelectProject() throws SQLException, IOException {
        final ObservableList<String> projects = FXCollections.observableArrayList();
        String query = "SELECT * FROM `szp`.`projekty`;";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()){
            //projects.add(rs.getString("ID_Projekt"));
            projects.add(rs.getString("Nazwa_projektu"));
        }
        comboBoxSelectProject.setItems(projects);
        //conn.close();
        //rs.close();
    }

    @FXML
    private void displayEmployeesStatusPracownik(ActionEvent event) throws SQLException {
        ObservableList<DataPracownicy> employees = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `szp`.`pracownicy` where `Stanowisko`='Pracownik';");
        while (rs.next()) {
            DataPracownicy emp = new DataPracownicy();
            emp.setPracownicyTable_id(rs.getInt("ID_Pracownik"));
            emp.setPracownicyTable_imie(rs.getString("Imie"));
            emp.setPracownicyTable_nazwisko(rs.getString("Nazwisko"));
            employees.add(emp);
        }
        pracownicyTable.setItems(employees);
    }

    @FXML
    private void displayEmployeesInProject(ActionEvent event) throws SQLException {
        String project = comboBoxSelectProject.getValue().toString();
        ObservableList<DataPracownicy> employeesInProject = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT pra.ID_pracownik, pra.Imie, pra.Nazwisko \n" +
                "FROM szp.pracownicy pra INNER JOIN szp.pracownicy_i_projekty pip INNER JOIN szp.projekty pro\n" +
                "WHERE pra.ID_Pracownik=pip.ID_Pracownik_FK\n" +
                "AND pro.ID_Projekt=pip.ID_Projekt_FK\n" +
                "AND pro.Nazwa_projektu='"+project+"';");
        while (rs.next()) {
            DataPracownicy ep = new DataPracownicy();
            ep.setPracownicyTable_id(rs.getInt("ID_Pracownik"));
            ep.setPracownicyTable_imie(rs.getString("Imie"));
            ep.setPracownicyTable_nazwisko(rs.getString("Nazwisko"));
            employeesInProject.add(ep);
        }
        pracownicyInProject_Table.setItems(employeesInProject);
    }

    @FXML
    private void addEmployee (ActionEvent e) throws SQLException{
        DataPracownicy person = pracownicyTable.getSelectionModel().getSelectedItem();//obiekt DataPracownicy zaznaczonego wiersza
        String id = person.getPracownicyTable_id().toString();
        System.out.println(id);


        /*Integer id_project;
        String project = comboBoxSelectProject.getValue().toString();
        ResultSet rs = conn.createStatement().executeQuery("select ID_Projekt from szp.projekty where Nazwa_projektu='"+project+"'");
        id_project = rs.getInt("ID_Projekt");
        System.out.println(id_project);*/


        try {
            String query = " insert into `szp`.`pracownicy_i_projekty` (`ID_Pracownik_FK`, `ID_Projekt_FK`)\n" +
                    "values (?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, id);
            preparedStmt.setString(2, "2");//powinna byc zmienna id_projektu wybranego z comboBoxa

            preparedStmt.executeUpdate();

            System.out.println("Rekord został wstawiony do tabeli projekty!");

        } catch (SQLException d) {

            System.out.println(d.getMessage());

        }
    }
}
