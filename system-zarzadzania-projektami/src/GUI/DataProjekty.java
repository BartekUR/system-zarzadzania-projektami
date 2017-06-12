package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Klasa zawierająca informacje o projektach
 */
public class DataProjekty {

    private final SimpleIntegerProperty projektyTable_idProperty = new SimpleIntegerProperty(0);
    private final SimpleStringProperty projektyTable_nazwaProperty = new SimpleStringProperty("");
    private final SimpleStringProperty projektyTable_headProperty = new SimpleStringProperty("");
    private final SimpleStringProperty projektyTable_statusProperty = new SimpleStringProperty("");
    private final SimpleStringProperty projektyTable_terminProperty = new SimpleStringProperty("");

    public DataProjekty(Integer id, String nazwa, String head, String status, String termin) {
        setProjektyTable_id(id);
        setProjektyTable_nazwa(nazwa);
        setProjektyTable_head(head);
        setProjektyTable_status(status);
        setProjektyTable_termin(termin);
    }

    public DataProjekty() {
        this(0, "", "", "", "");
    }

    public Integer getProjektyTable_id() {
        return projektyTable_idProperty.get();
    }

    public void setProjektyTable_id(Integer id) {
        projektyTable_idProperty.set(id);
    }

    public String getProjektyTable_nazwa() {
        return projektyTable_nazwaProperty.get();
    }

    public void setProjektyTable_nazwa(String nazwa) {
        projektyTable_nazwaProperty.set(nazwa);
    }

    public String getProjektyTable_head() {
        return projektyTable_headProperty.get();
    }

    public void setProjektyTable_head(String head) {
        projektyTable_headProperty.set(head);
    }

    public String getProjektyTable_status() {
        return projektyTable_statusProperty.get();
    }

    public void setProjektyTable_status(String status) {
        projektyTable_statusProperty.set(status);
    }

    public String getProjektyTable_termin() {
        return projektyTable_terminProperty.get();
    }

    public void setProjektyTable_termin(String termin) { projektyTable_terminProperty.set(termin); }
}
