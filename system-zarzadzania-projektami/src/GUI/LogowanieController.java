package GUI;

import Utils.Checks;
import Utils.MyIOException;
import Utils.MySqlQueryException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/**
 * Klasa obsługująca logowanie
 */

public class LogowanieController {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();
    public static String who, pass, what, whoLogin;

    @FXML private TextField log_user;
    @FXML private PasswordField log_pass;
    @FXML private Button loginButton;

    /**
     * Metoda obsługująca logowanie
     */
    public void login() throws MySqlQueryException, MyIOException {
        Checks check = new Checks();
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM `szp`.`pracownicy` WHERE Login=?");
            pst.setString(1, log_user.getText());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                who = rs.getString("Imie") + " " + rs.getString("Nazwisko");
                whoLogin = rs.getString("Login");
                what = rs.getString("Stanowisko");
                pass = rs.getString("Haslo");

                if (check.checkPass(log_pass.getText(), pass))
                    System.out.println("Zostałeś zalogowany jako " + who + " ze stanowiskiem " + what + ".");
                else {
                    System.out.println(log_pass.getText() + " != " + pass);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Niepoprawne hasło!");
                    alert.setHeaderText(null);
                    alert.setContentText("Spróbuj ponownie wprowadzić hasło.");
                    alert.showAndWait();
                    return;
                }

                Stage oldStage = (Stage) loginButton.getScene().getWindow();
                oldStage.close();
                Parent newroot;
                try {
                    newroot = FXMLLoader.load(getClass().getResource(what + "GUI.fxml"));
                } catch(IOException e) {
                    throw new MyIOException(e);
                }
                Scene newscene = new Scene(newroot);
                Stage newStage = new Stage();
                newStage.setTitle("System zarządzania projektami 1.0");
                newStage.setResizable(false);
                newStage.setScene(newscene);
                newStage.setMaxWidth(800);
                newStage.setMaxHeight(600);
                newStage.setMinWidth(800);
                newStage.setMinHeight(600);
                newStage.show();
            } else {
                System.out.println("Niepoprawne dane! Sprobuj ponownie.");
            }
        } catch (SQLException e) {
            throw new MySqlQueryException(e);
        }
    }

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
