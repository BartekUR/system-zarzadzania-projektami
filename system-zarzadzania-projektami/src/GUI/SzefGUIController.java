package GUI;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import javax.swing.text.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import static GUI.LogowanieController.who;


/**
 * Klasa obsługująca GUI szefa
 */

public class SzefGUIController implements Initializable {

    public Button genRaport;
    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();

    @FXML private TableView<DataPracownicy> pracownicyTable;
    @FXML private TableColumn<DataPracownicy, Integer> pracownicyTable_id;
    @FXML private TableColumn<DataPracownicy, String> pracownicyTable_imie, pracownicyTable_nazwisko, pracownicyTable_stanowisko;

    @FXML private TableView<DataProjekty> projektyTable;
    @FXML private TableColumn<DataProjekty, Integer> projektyTable_id;
    @FXML private TableColumn<DataProjekty, String> projektyTable_nazwa, projektyTable_head, projektyTable_status, projektyTable_termin;

    @FXML private TextField nazwaProjektu;
    @FXML private DatePicker termin_koncowyProjektu;
    @FXML private ComboBox comboBoxSzef, comboBoxStatus;
    @FXML private Label labelProjektWstawiony;

    @FXML private Button addUser;
    @FXML private Button editUser;

    String newLine = System.getProperty("line.separator");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pracownicyTable_id.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_id"));
        pracownicyTable_imie.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_imie"));
        pracownicyTable_nazwisko.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_nazwisko"));
        pracownicyTable_stanowisko.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_stanowisko"));
        projektyTable_id.setCellValueFactory(new PropertyValueFactory<>("projektyTable_id"));
        projektyTable_nazwa.setCellValueFactory(new PropertyValueFactory<>("projektyTable_nazwa"));
        projektyTable_head.setCellValueFactory(new PropertyValueFactory<>("projektyTable_head"));
        projektyTable_status.setCellValueFactory(new PropertyValueFactory<>("projektyTable_status"));
        projektyTable_termin.setCellValueFactory(new PropertyValueFactory<>("projektyTable_termin"));

        System.out.println("Zainicjalizowano kontroler Szefa dla: " + who);

        try {
            refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList <String> statusProjektuList = FXCollections.observableArrayList("Rozpoczety","W trakcie","Opozniony", "Gotowy" );
        comboBoxStatus.setItems(statusProjektuList);
    }

    /**
     * Metoda do wyświetalnia pacowników w tablicy
     */

    private void wyswietlPracownikowTable() throws SQLException {
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
        pracownicyTable.refresh();
    }

    /**
     * Metoda do wyświetlania projektów
     */

    private void wyswietlProjektyTable() throws SQLException {
        ObservableList<DataProjekty> data = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `szp`.`projekty`;");
        while (rs.next()) {
            DataProjekty dp = new DataProjekty();
            dp.setProjektyTable_id(rs.getInt("ID_Projekt"));
            dp.setProjektyTable_nazwa(rs.getString("Nazwa_projektu"));
            dp.setProjektyTable_head(rs.getString("Head"));
            dp.setProjektyTable_status(rs.getString("Status"));
            dp.setProjektyTable_termin(rs.getString("Termin"));
            data.add(dp);
        }
        projektyTable.setItems(data);
        projektyTable.refresh();
    }

    /**
     * Metoda do obsługiwania comboboxa z headami
     */

    @FXML
    private void wyswietlHeadowCombo() throws SQLException {
        ObservableList<String> options = FXCollections.observableArrayList();
        String query = "SELECT * FROM `szp`.`pracownicy` where `Stanowisko`='Head';";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            options.add(rs.getString("Imie") + " " + rs.getString("Nazwisko"));
        }
        comboBoxSzef.setItems(options);
    }

    /**
     * Metoda do obsługiwania przycisku do usuwania projektów
     */

    @FXML
    private void usunProjekt() throws SQLException {
        DataProjekty projectDelete = projektyTable.getSelectionModel().getSelectedItem();

        if(projectDelete != null) {
            String id_project = projectDelete.getProjektyTable_id().toString();
            try {
                String query = "DELETE FROM szp.projekty WHERE ID_Projekt=" + id_project + ";";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.executeUpdate();
                System.out.println("Projekt został usunięty!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            wyswietlProjektyTable();
        }
    }

    /**
     * Metoda do obsługiwania przycisku służącego do dodawania projektów
     */

    @FXML
    private void dodajProjekt() throws SQLException  {
        String name = nazwaProjektu.getText();
        String head = comboBoxSzef.getValue().toString();
        String status = comboBoxStatus.getValue().toString();
        String termin = termin_koncowyProjektu.getValue().toString();
        int numberOfRows = 0;

        if (name.length() == 0 || status.length() == 0 ) {
            System.out.println("Wypełnij wszystkie pola");
        } else {
            try {
                String query_exists = "SELECT COUNT(*) AS total FROM `szp`.`projekty` WHERE `Nazwa_projektu`=(?) AND `Head`=(?) AND `Status`=(?) AND `Termin`=(?)";
                PreparedStatement preparedStmte = conn.prepareStatement(query_exists);
                preparedStmte.setString(1, name);
                preparedStmte.setString(2, head);
                preparedStmte.setString(3, status);
                preparedStmte.setString(4, termin);
                ResultSet rs = preparedStmte.executeQuery();
                while(rs.next())
                {
                    numberOfRows = rs.getInt("total");
                }
                try {
                    if (numberOfRows == 0) {

                    String query = " insert into `szp`.`projekty` (`Nazwa_projektu`, `Head`, `Status`, `Termin`)"
                            + " values (?, ?, ?, ?)";

                    PreparedStatement pst = conn.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setString(2, head);
                    pst.setString(3, status);
                    pst.setString(4, termin);

                    pst.executeUpdate();
                    labelProjektWstawiony.setVisible(true);
                    System.out.println("Rekord został wstawiony do tabeli projekty!");
                    } else if(numberOfRows >= 1){
                        System.out.println("Rekord juz istnieje");
                    }
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
            refresh();
    }

    /**
     * Metoda do obsługiwania przycisku do dodawania użytkowników
     */

    @FXML
    private void dodajUzytkownika() throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
        Scene info_scene= new Scene(loader);
        Stage info_stage =new Stage();
        info_stage.setScene(info_scene);
        info_stage.initModality(Modality.APPLICATION_MODAL);
        info_stage.initOwner(addUser.getScene().getWindow());
        info_stage.showAndWait();
    }

    /**
     * Metoda służąca do obsługiwania przycisku do usuwania użytkownika
     */

    @FXML
    private void usunUzytkownika() throws IOException,SQLException {
        DataPracownicy person = pracownicyTable.getSelectionModel().getSelectedItem();

        if(person != null) {
            String id = person.getPracownicyTable_id().toString();
            try {
                String query = "DELETE FROM szp.pracownicy WHERE ID_Pracownik=(?) and `Stanowisko` !='Szef';";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, id);
                preparedStmt.executeUpdate();                
                wyswietlPracownikowTable();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * Metoda do obsługi przycisku do edytowania użytkownika
     */

    @FXML
    private void edytujUzytkownika() throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("EditUser.fxml"));
        Scene info_scene= new Scene(loader);
        Stage info_stage =new Stage();
        info_stage.setScene(info_scene);
        info_stage.initModality(Modality.APPLICATION_MODAL);
        info_stage.initOwner(editUser.getScene().getWindow());
        info_stage.showAndWait();
    }

    /**
     * Metoda służaca do obsługi przycisku wypełniającego przykładowymi danymi baze danych
     */

    @FXML
    private void wypelnijBaze() throws SQLException, IOException {
        String line;

        BufferedReader br = new BufferedReader(new FileReader("./system-zarzadzania-projektami/db_test.sql"));
        System.out.println("Wypełnianie bazy testowymi danymi...");
        Statement stmt = conn.createStatement();
        while ((line = br.readLine()) != null) {
            if (line.length() != 0)
                stmt.executeUpdate(line);
        }
        conn.commit();
        refresh();
    }

    /**
     * Metoda do generowania pdfów
     */
    @FXML
    private void generujRaportSzefa(ActionEvent event)throws IOException, SQLException {

        try{

            Document document= new Document();
            PdfWriter.getInstance(document,new FileOutputStream("./system-zarzadzania-projektami/raport_szefa.pdf"));

            document.open();
            Paragraph p1=new Paragraph("Gotowe projekty: ");
            p1.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            PdfPCell cell = new PdfPCell(new Phrase("Nazwa projektu"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Head"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Status"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Data zakonczenia"));
            table.addCell(cell);
            table.setHeaderRows(1);
            try {

                String query= "SELECT Nazwa_projektu, Head, `Status`, Termin FROM szp.projekty WHERE `Status`='Gotowy';";
                PreparedStatement preparedStmte = conn.prepareStatement(query);
                ResultSet rs = preparedStmte.executeQuery();

                while (rs.next()) {

                table.addCell(rs.getString("Nazwa_projektu"));
                table.addCell(rs.getString("Head"));
                table.addCell(rs.getString("Status"));
                table.addCell(rs.getString("Termin"));
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
            document.add(p1);
            document.add(table);
            document.close();
            System.out.println("Pdf został wygenerowany jego lokalizacja to:./system-zarzadzania-projektami/raport_heada.pdf ");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Metoda do odświeżania
     */

    private void refresh() throws SQLException {
        try {
            wyswietlPracownikowTable();
            wyswietlProjektyTable();
            wyswietlHeadowCombo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TextField getNazwaProjektu() {
        return nazwaProjektu;
    }

    public void setNazwaProjektu(TextField nazwaProjektu) {
        this.nazwaProjektu = nazwaProjektu;
    }

    public void generowanieRaport(ActionEvent actionEvent) {
    }
}

