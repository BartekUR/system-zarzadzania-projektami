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

    @FXML private TableView<DataProjekty> tableProjektPracownika;
    @FXML private TableColumn<DataProjekty, String> columnProjekt;
    @FXML private TableColumn<DataProjekty, String> columnHead;
    @FXML private TableColumn<DataProjekty, String> columnStatus;
    @FXML private TableColumn<DataProjekty, String> columnTermin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnProjekt.setCellValueFactory(new PropertyValueFactory<>("projektyTable_nazwa"));
        columnHead.setCellValueFactory(new PropertyValueFactory<>("projektyTable_head"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("projektyTable_status"));
        columnTermin.setCellValueFactory(new PropertyValueFactory<>("projektyTable_termin"));

        System.out.println("Zainicjalizowano kontroler Pracownika dla: " + who);

        try {
            wyswietlProjektyPracownika();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void wyswietlProjektyPracownika() throws SQLException {
        ObservableList<DataProjekty> dataProjekty2 = FXCollections.observableArrayList();

        ResultSet rs = conn.createStatement().executeQuery("SELECT pro.`Nazwa_projektu`, pro.`Head`, pro.`Status`, pro.`Termin`\n" +
                "FROM szp.projekty pro , szp.pracownicy_i_projekty pip, szp.pracownicy pra\n" +
                "WHERE pro.ID_Projekt = pip.ID_Projekt_FK\n" +
                "AND pra.ID_Pracownik = pip.ID_Pracownik_FK\n" +
                "AND pra.Login ='" + whoLogin + "'");

        while (rs.next()) {
            DataProjekty dp = new DataProjekty();
            dp.setProjektyTable_nazwa(rs.getString("Nazwa_projektu"));
            dp.setProjektyTable_head(rs.getString("Head"));
            dp.setProjektyTable_status(rs.getString("Status"));
            dp.setProjektyTable_termin(rs.getString("Termin"));
            dataProjekty2.add(dp);
            System.out.println(dataProjekty2);
        }

        tableProjektPracownika.setItems(dataProjekty2);
        tableProjektPracownika.refresh();
    }
}