package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
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

    @FXML
    private TableView<DataProjektyPracownika> tableProjektPracownika;
    @FXML
    private TableColumn<DataProjektyPracownika, String> columnProjekt;
    @FXML
    private TableColumn<DataProjektyPracownika, String> columnHead;
    @FXML
    private TableColumn<DataProjektyPracownika, String> columnStatus;
    @FXML
    private TableColumn<DataProjektyPracownika, String> columnTermin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnProjekt.setCellValueFactory(new PropertyValueFactory<>("columnProjekt"));
        columnHead.setCellValueFactory(new PropertyValueFactory<>("columnHead"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("columnStatus"));
        columnTermin.setCellValueFactory(new PropertyValueFactory<>("columnTermin"));

        System.out.println("Zainicjalizowano kontroler Pracownika dla: " + who);

        try {
            wyswietlProjektyPracownika();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void wyswietlProjektyPracownika() throws SQLException {
        ObservableList<DataProjektyPracownika> dataProjekty = FXCollections.observableArrayList();

        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT pro.`Nazwa_projektu`, pro.`Head`, pro.`Status`, pro.`Termin`\n" +
                    "FROM szp.projekty pro , szp.pracownicy_i_projekty pip, szp.pracownicy pra\n" +
                    "WHERE pro.ID_Projekt = pip.ID_Projekt_FK\n" +
                    "AND pra.ID_Pracownik = pip.ID_Pracownik_FK\n" +
                    "AND pra.Login ='" + whoLogin + "'");
            while (rs.next()) {
                DataProjektyPracownika dpp = new DataProjektyPracownika();
                dpp.setProjektyPracownika_nazwa(rs.getString("Nazwa_projektu"));
                dpp.setProjektyPracownika_head(rs.getString("Head"));
                dpp.setProjektyPracownika_status(rs.getString("Status"));
                dpp.setProjektyPracownika_termin(rs.getString("Termin"));
                dataProjekty.add(dpp);
                System.out.println(dataProjekty);
            }


        } catch (SQLException d) {
            System.out.println(d.getMessage());
        }
        tableProjektPracownika.setItems(dataProjekty);
    }
}