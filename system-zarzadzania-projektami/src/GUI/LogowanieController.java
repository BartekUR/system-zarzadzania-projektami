package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.*;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Michal on 2017-03-22.
 */
public class LogowanieController {

    private SqlConnect sc = new SqlConnect();
    private Connection conn = sc.getConn();
    public static String who, what, whoLogin;

    @FXML
    private TextField log_user;
    @FXML
    private PasswordField log_pass;
    @FXML
    private Button loginButton;


    public void login() throws SQLException, IOException {
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM `szp`.`pracownicy` WHERE Login=? AND Haslo=?");
        pst.setString(1, log_user.getText());
        pst.setString(2, log_pass.getText());
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            who = rs.getString("Imie") + " " + rs.getString("Nazwisko");
            whoLogin = rs.getString( "Login");
            what = rs.getString("Stanowisko");
            System.out.println("Zostałeś zalogowany jako " + who + " ze stanowiskiem " + what + ".");

            Stage oldStage = (Stage) loginButton.getScene().getWindow();
            oldStage.close();
            Stage newStage = new Stage();
            Parent newroot = FXMLLoader.load(getClass().getResource(what + "GUI.fxml"));
            Scene newscene = new Scene(newroot);
            newStage.setTitle("System zarządzania projektami 1.0");
            newStage.setScene(newscene);
            newStage.show();
        } else {
            System.out.println("Niepoprawne dane! Sprobuj ponownie.");
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
