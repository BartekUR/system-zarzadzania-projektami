package GUI;

import Utils.MyIOException;
import Utils.MySqlCantConnectException;
import Utils.MySqlQueryException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

import static GUI.LogowanieController.who;

/**
 * Klasa  osługująca GUI Heada
 */
public class HeadGUIController implements Initializable  {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();
    
    public TextField nazwaTasku;
    public DatePicker terminTasku;

    @FXML private TableView<DataPracownicy> pracownikTable, pracownicyInProject_Table;
    @FXML private TableColumn<DataPracownicy, Integer> pracownikTable_id, pracownicyInProject_Table_id;
    @FXML private TableColumn<DataPracownicy, String> pracownikTable_imie,  pracownicyInProject_Table_imie;
    @FXML private TableColumn<DataPracownicy, String> pracownikTable_nazwisko, pracownicyInProject_Table_nazwisko;

    @FXML private TableView<DataTaski> mojeProjekty;
    @FXML private TableColumn<DataTaski, Integer> idMProjekty;
    @FXML private TableColumn<DataTaski, String> taskMProjekty, pracownikMProjekty, statusMProjekty, terminMProjekty;

    @FXML private TableView<DataTaski> taskiTable1, taskiTable2, taskiTable3;
    @FXML private TableColumn<DataTaski, Integer> taskiTable_id, taskiTable2_id, taskiTable_id1;
    @FXML private TableColumn<DataTaski, String> taskiTable_nazwa, taskiTable2_nazwa, taskiTable_nazwa1;

    @FXML private ComboBox comboBoxProjects, comboBoxHead, comboBoxSelectProject2, comboBoxSelectPracownik, comboBoxProjektHeada2;

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

        pracownicyInProject_Table_id.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_id"));
        pracownicyInProject_Table_imie.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_imie"));
        pracownicyInProject_Table_nazwisko.setCellValueFactory(new PropertyValueFactory<>("pracownicyTable_nazwisko"));

        taskiTable_id.setCellValueFactory(new PropertyValueFactory<>("taskiTable_id"));
        taskiTable_nazwa.setCellValueFactory(new PropertyValueFactory<>("taskiTable_nazwa"));

        taskiTable2_id.setCellValueFactory(new PropertyValueFactory<>("taskiTable_id"));
        taskiTable2_nazwa.setCellValueFactory(new PropertyValueFactory<>("taskiTable_nazwa"));

        taskiTable_id1.setCellValueFactory(new PropertyValueFactory<>("taskiTable_id"));
        taskiTable_nazwa1.setCellValueFactory(new PropertyValueFactory<>("taskiTable_nazwa"));

        System.out.println("Zainicjalizowano kontroler Heada dla: " + who);

        try {
            wyswietlProjektyHeadaCombo();
            wyswietlPracownikowTable();
        } catch (MySqlQueryException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda do wyświetlania pracowników w tabeli
     * @throws MySqlCantConnectException
     */
    @FXML
    private void wyswietlPracownikowTable() throws MySqlQueryException {

        ObservableList<DataPracownicy> employees = FXCollections.observableArrayList();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `szp`.`pracownicy` where `Stanowisko`='Pracownik';");

            while (rs.next()) {
                DataPracownicy emp = new DataPracownicy();
                emp.setPracownicyTable_id(rs.getInt("ID_Pracownik"));
                emp.setPracownicyTable_imie(rs.getString("Imie"));
                emp.setPracownicyTable_nazwisko(rs.getString("Nazwisko"));
                employees.add(emp);
            }
        } catch(SQLException e) {
            throw new MySqlQueryException(e);
        }

        pracownikTable.setItems(employees);
        pracownikTable.refresh();
    }

    /**
     * Metoda do wyświetlania pracowników z danego projektu
     * @throws MySqlCantConnectException
     */
    @FXML
    private void wyswietlPracownikowProjektuTable() throws MySqlQueryException {

        String name_project = comboBoxProjektHeada2.getValue().toString();

        if(name_project != null) {
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
                throw new MySqlQueryException(e);
            }
            pracownicyInProject_Table.setItems(employeesInProject);
        }

    }

    /**
     * Metoda do dodawania pracownika do danego proejtku
     * @throws MySqlCantConnectException
     */
    @FXML
    private void dodajPracownikaDoProjektu() throws MySqlQueryException {
        DataPracownicy person = pracownikTable.getSelectionModel().getSelectedItem();//obiekt DataPracownicy zaznaczonego wiersza
        String nazwa_projektu = comboBoxProjektHeada2.getValue().toString();
        int id_project = 0;

        try {
            String query_id = "SELECT ID_Projekt AS id FROM szp.projekty WHERE Nazwa_projektu=(?);";
            PreparedStatement pS = conn.prepareStatement(query_id);
            pS.setString(1, nazwa_projektu);
            ResultSet rs = pS.executeQuery();
            while(rs.next())
            {
                id_project = rs.getInt("id");
            }
        }catch (SQLException e){
            throw new MySqlQueryException(e);
        }
        System.out.println("ID projektu to "+id_project);


        if(person!=null && nazwa_projektu!=null){
            String id_person = person.getPracownicyTable_id().toString();
            try {
                String query = " insert into `szp`.`pracownicy_i_projekty` (`ID_Pracownik_FK`, `ID_Projekt_FK`) values (?, ?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, id_person);
                pst.setInt(2, id_project);
                pst.executeUpdate();

                System.out.println("Rekord został wstawiony do tabeli projekty!");
            } catch (SQLException e){
                throw new MySqlQueryException(e);
            }
            wyswietlPracownikowProjektuTable();
        }
    }

    /**
     * Metoda do usuwania pracownika z danego projektu
     * @throws MySqlCantConnectException
     */
    @FXML
    private void usunPracownikaZProjektu() throws MySqlQueryException {
        DataPracownicy personDelete = pracownicyInProject_Table.getSelectionModel().getSelectedItem();
        String projectDel = comboBoxProjektHeada2.getValue().toString();
        int id_projectDel = 0;

        try {
            String query_id = "SELECT ID_Projekt AS id FROM szp.projekty WHERE Nazwa_projektu=(?);";
            PreparedStatement pS = conn.prepareStatement(query_id);
            pS.setString(1, projectDel);
            ResultSet rs = pS.executeQuery();
            while(rs.next())
            {
                id_projectDel = rs.getInt("id");
            }
        }catch (SQLException e){
            throw new MySqlQueryException(e);
        }
        System.out.println("ID projektu to "+id_projectDel);


        if(personDelete != null && projectDel != null) {
            String id_pracownik = personDelete.getPracownicyTable_id().toString();
            try {
                String query = "DELETE FROM szp.pracownicy_i_projekty WHERE ID_Pracownik_FK=(?) AND ID_Projekt_FK=(?);";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, id_pracownik);
                pst.setInt(2, id_projectDel);

                pst.executeUpdate();
                System.out.println("Pracownik został usunięty z projektu!");
            } catch (SQLException e) {
                throw new MySqlQueryException(e);
            }
            wyswietlPracownikowProjektuTable();
        }

    }

    /**
     * Metoda do przydzielania pracownika do tasku
     * @throws MySqlCantConnectException
     */
    @FXML
    private void dodajPracownikaDoTasku() throws MySqlQueryException {
        String[] pracownik = comboBoxSelectPracownik.getValue().toString().split(" ", 2);
        String pimie = pracownik[0];
        String pnazwisko = pracownik[1];
        System.out.println("Imie: " + pimie + " i nazwisko " + pnazwisko);
        int id_pracownik = 0;

        try {
            String query_id = "SELECT ID_Pracownik AS id  FROM szp.pracownicy WHERE Imie = (?) AND Nazwisko = (?);";
            PreparedStatement pS = conn.prepareStatement(query_id);
            pS.setString(1, pimie);
            pS.setString(2, pnazwisko);
            ResultSet rs = pS.executeQuery();
            while(rs.next())
            {
                id_pracownik = rs.getInt("id");
            }
        }catch (SQLException e){
            throw new MySqlQueryException(e);
        }
        System.out.println("ID pracownika to "+id_pracownik);
        DataTaski task = taskiTable1.getSelectionModel().getSelectedItem();


        if(task!=null && id_pracownik!=0){
            int id_task = task.getTaskiTable_id();
            System.out.println("ID tasku to "+id_task);
            try {
                String query = " insert into `szp`.`pracownicy_i_taski` (`ID_Pracownik_FK`, `ID_Taski_FK`) values (?, ?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, id_pracownik);
                pst.setInt(2, id_task);
                pst.executeUpdate();

                System.out.println("Pracownik został dodany do tasku!");
            } catch (SQLException e){
                throw new MySqlQueryException(e);
            }
            wyswietlTaskiPracownikaProjektu();
        }
    }

    /**
     * Metoda do usuwania pracownika z danego tasku
     * @throws MySqlCantConnectException
     */
    @FXML
    private void usunPracownikaZTasku() throws MySqlQueryException {
        String[] pracownik = comboBoxSelectPracownik.getValue().toString().split(" ", 2);
        String pimie = pracownik[0];
        String pnazwisko = pracownik[1];
        System.out.println("Imie: " + pimie + " i nazwisko " + pnazwisko);
        int id_pracownik = 0;

        try {
            String query_id = "SELECT ID_Pracownik  AS id  FROM szp.pracownicy WHERE Imie = (?) AND Nazwisko = (?);";
            PreparedStatement pS = conn.prepareStatement(query_id);
            pS.setString(1, pimie);
            pS.setString(2, pnazwisko);
            ResultSet rs = pS.executeQuery();
            while(rs.next())
            {
                id_pracownik = rs.getInt("id");
            }
        }catch (SQLException e){
            throw new MySqlQueryException(e);
        }
        System.out.println("ID pracownika to "+id_pracownik);
        DataTaski task = taskiTable2.getSelectionModel().getSelectedItem();

        if(task!=null && id_pracownik!=0){
            int id_task = task.getTaskiTable_id();
            System.out.println("ID tasku to "+id_task);
            try {
                String query = "DELETE FROM szp.pracownicy_i_taski WHERE ID_Pracownik_FK=(?) AND ID_Taski_FK=(?);";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, id_pracownik);
                pst.setInt(2, id_task);
                pst.executeUpdate();

                System.out.println("Pracownik został usunięty z tasku!");
            } catch (SQLException e){
                throw new MySqlQueryException(e);
            }
            wyswietlTaskiPracownikaProjektu();
        }

    }

    /**
     * Metoda do wyświetlania tasków danego projektu
     * @throws MySqlCantConnectException
     */

    @FXML
    private void wyswietlTaskiProjektuTable1() throws MySqlQueryException {

        ObservableList<DataTaski> data = FXCollections.observableArrayList();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT t.ID_Task, t.Nazwa_tasku " +
                    "FROM  szp.taski t, szp.projekty pro " +
                    "WHERE pro.ID_Projekt=t.ID_Projekt_FK " +
                    "AND pro.Nazwa_projektu='" + comboBoxSelectProject2.getValue().toString() + "';");

            while (rs.next()) {
                DataTaski dt = new DataTaski();
                dt.setTaskiTable_id(rs.getInt("ID_Task"));
                dt.setTaskiTable_nazwa(rs.getString("Nazwa_tasku"));
                data.add(dt);
            }
        } catch(SQLException e) {
            throw new MySqlQueryException(e);
        }

        taskiTable1.setItems(data);
        taskiTable1.refresh();
        wyswietlPracownikowCombo();
    }

    /**
     * Metoda do wyswietlania tasków danego pracownika
     * @throws MySqlCantConnectException
     */
    @FXML
    private void wyswietlTaskiPracownikaProjektu() throws MySqlQueryException {

        String[] pracownik = comboBoxSelectPracownik.getValue().toString().split(" ", 2);
        String pimie = pracownik[0];
        String pnazwisko = pracownik[1];
        System.out.println("Imie: " + pimie + " i nazwisko " + pnazwisko);
        int id_pracownik = 0;
        String nazwa_projektu = comboBoxSelectProject2.getValue().toString();

        try {
            String query_id = "SELECT ID_Pracownik  AS id  FROM szp.pracownicy WHERE Imie = (?) AND Nazwisko = (?);";
            PreparedStatement pS = conn.prepareStatement(query_id);
            pS.setString(1, pimie);
            pS.setString(2, pnazwisko);
            ResultSet rs = pS.executeQuery();
            while(rs.next())
            {
                id_pracownik = rs.getInt("id");
            }
        }catch (SQLException e){
            throw new MySqlQueryException(e);
        }
        System.out.println("ID pracownika to "+id_pracownik);

        ObservableList<DataTaski> data = FXCollections.observableArrayList();
        try {
            String query_id = "SELECT t.ID_Task, t.Nazwa_tasku " +
                    "FROM szp.taski t, szp.pracownicy pra, szp.pracownicy_i_taski pit, szp.projekty pro " +
                    "WHERE pra.ID_Pracownik=(?) " +
                    "AND pra.ID_Pracownik=pit.ID_Pracownik_FK " +
                    "AND t.ID_Task=pit.ID_Taski_FK " +
                    "AND t.ID_Projekt_FK=pro.ID_Projekt " +
                    "AND pro.Nazwa_projektu=(?);";
            PreparedStatement pS = conn.prepareStatement(query_id);
            pS.setInt(1, id_pracownik);
            pS.setString(2, nazwa_projektu);
            ResultSet rs = pS.executeQuery();

            while (rs.next()) {
                DataTaski dt = new DataTaski();
                dt.setTaskiTable_id(rs.getInt("ID_Task"));
                dt.setTaskiTable_nazwa(rs.getString("Nazwa_tasku"));
                data.add(dt);
            }
        }catch (SQLException e){
            throw new MySqlQueryException(e);
        }

        taskiTable2.setItems(data);
        taskiTable2.refresh();
    }

    /**
     * Metoda do wyświetlania tasków danego projektu
     * @throws MySqlCantConnectException
     */
    @FXML
    private void wyswietlTaskiProjektuTable2() throws MySqlQueryException {

        ObservableList<DataTaski> data = FXCollections.observableArrayList();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT t.ID_Task, t.Nazwa_tasku " +
                    "FROM szp.taski t, szp.projekty pro " +
                    "WHERE pro.ID_Projekt=t.ID_Projekt_FK " +
                    "AND pro.Nazwa_projektu='" + comboBoxProjects.getValue().toString() + "';");

            while (rs.next()) {
                DataTaski dt = new DataTaski();
                dt.setTaskiTable_id(rs.getInt("ID_Task"));
                dt.setTaskiTable_nazwa(rs.getString("Nazwa_tasku"));
                data.add(dt);
            }
        } catch(SQLException e) {
            throw new MySqlQueryException(e);
        }

        taskiTable3.setItems(data);
        taskiTable3.refresh();
    }

    /**
     * Metoda do wyświetlania projektu wybranego z comboboxa
     * @throws MySqlCantConnectException
     */
    @FXML
    private void wyswietlProjekt() throws MySqlQueryException {

        ObservableList<DataTaski> data = FXCollections.observableArrayList();
        try {
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
        } catch(SQLException e) {
            throw new MySqlQueryException(e);
        }

        mojeProjekty.setItems(data);
        mojeProjekty.refresh();
    }

    /**
     * Metoda obsługująca combobox wyświetlający proejtky heada
     * @throws MySqlQueryException
     */
    @FXML
    public void wyswietlProjektyHeadaCombo() throws MySqlQueryException {

        ObservableList<String> options = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM `szp`.`projekty` WHERE `Head`='" + who + "'";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("Nazwa_projektu"));
            }
        } catch (SQLException e) {
            throw new MySqlQueryException(e);
        }

        comboBoxProjects.setItems(options);
        comboBoxHead.setItems(options);
        comboBoxSelectProject2.setItems(options);
        comboBoxProjektHeada2.setItems(options);
    }

    /**
     * Metoda obsługująca combobox wyświetlający pracowników
     * @throws MySqlQueryException
     */
    @FXML
    public void wyswietlPracownikowCombo() throws MySqlQueryException {

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
            throw new MySqlQueryException(e);
        }
    }

    /**
     * Metoda obsługująca przycisk do usuwania tasków
     * @throws MySqlCantConnectException
     */
    @FXML
    private void usunTask() throws MySqlQueryException {
        DataTaski taskDelete = taskiTable3.getSelectionModel().getSelectedItem();

        if (taskDelete != null) {
            String id_task = taskDelete.getTaskiTable_id().toString();
            try {
                String query = "DELETE FROM szp.taski WHERE ID_Task=" + id_task + ";";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.executeUpdate();
                System.out.println("Task został usunięty z projektu!");
            } catch (SQLException e) {
                throw new MySqlQueryException(e);
            }

            wyswietlTaskiProjektuTable2();
        }
    }

    /**
     * Metoda do obsługiwania przycisku służącego do dodawania tasków
     * @throws MySqlQueryException
     */
     public void dodajTask() throws MySqlQueryException {
        String task = nazwaTasku.getText();
        String termin = terminTasku.getValue().toString();
        String projekt = comboBoxProjects.getValue().toString();
        int numberOfRows = 0;
        int id_project = 0;
        String status = "Rozpoczęty";

        try {
            String query_id = "SELECT ID_Projekt AS id FROM szp.projekty WHERE Nazwa_projektu = (?);";
            PreparedStatement pS = conn.prepareStatement(query_id);
            pS.setString(1, projekt);
            ResultSet rs = pS.executeQuery();
            while(rs.next())
            {
                id_project = rs.getInt("id");
            }
        } catch (SQLException e){
            throw new MySqlQueryException(e);
        }

        if (task.length() !=0 || termin.length() !=0) {
            try {
                String query_exists = "SELECT COUNT(*) AS total FROM `szp`.`taski`\n" +
                        "WHERE `ID_Projekt_FK`=(?) \n" +
                        "AND `Nazwa_tasku`=(?)\n" +
                        "AND `Status`=(?) \n" +
                        "AND `Termin`=(?);\n";
                PreparedStatement preparedStmte = conn.prepareStatement(query_exists);
                preparedStmte.setInt(1, id_project);
                preparedStmte.setString(2, task);
                preparedStmte.setString(3, status);
                preparedStmte.setString(4, termin);
                ResultSet rs = preparedStmte.executeQuery();
                while(rs.next()) {
                    numberOfRows = rs.getInt("total");
                }
                if (numberOfRows == 0) {

                    String query = " INSERT INTO `szp`.`taski` (`ID_Projekt_FK`, `Nazwa_tasku`, `Status`, `Termin`) \n" +
                            "values (?, ?, ?, ?);";

                    PreparedStatement pst = conn.prepareStatement(query);
                    pst .setInt(1, id_project);
                    pst .setString(2, task);
                    pst .setString(3, status);
                    pst .setString(4, termin);

                    pst.executeUpdate();

                    System.out.println("Rekord został wstawiony");
                } else if(numberOfRows >= 1) {
                    System.out.println("Rekord juz istnieje");
                }
            } catch (SQLException e) {
                throw new MySqlQueryException(e);
            }
        } else {
            System.out.println("Wypełnij pola");
        }
         wyswietlTaskiProjektuTable2();
     }

    /**
     * Metoda do generowania pdfów
     * @throws MyIOException
     */
    @FXML
    private void generujRaportHeada() throws MyIOException, DocumentException, SQLException {

        try{

            Document document= new Document();
            PdfWriter.getInstance(document,new FileOutputStream("./system-zarzadzania-projektami/raport_heada.pdf"));

            document.open();
            Paragraph p1=new Paragraph("Rozpoczete projekty: ");
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

            String query= "SELECT Nazwa_projektu, Head, `Status`, Termin FROM szp.projekty WHERE `Status`='Rozpoczęty';";
            PreparedStatement preparedStmte = conn.prepareStatement(query);
            ResultSet rs = preparedStmte.executeQuery();

            while (rs.next()) {

                table.addCell(rs.getString("Nazwa_projektu"));
                table.addCell(rs.getString("Head"));
                table.addCell(rs.getString("Status"));
                table.addCell(rs.getString("Termin"));
            }
            document.add(p1);
            document.add(table);
            document.close();
            System.out.println("Pdf został wygenerowany jego lokalizacja to:./system-zarzadzania-projektami/raport_heada.pdf ");
        }
        catch(IOException e) {
            throw new MyIOException(e);
        }
    }
}
