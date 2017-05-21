package GUI;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Przemek on 21.05.2017.
 */
public class DataProjektyPracownika {
    private final SimpleStringProperty projektyPracownika_nazwaProperty = new SimpleStringProperty("");
    private final SimpleStringProperty projektyPracownika_headProperty = new SimpleStringProperty("");
    private final SimpleStringProperty projektyPracownika_statusProperty = new SimpleStringProperty("");
    private final SimpleStringProperty projektyPracownika_terminProperty = new SimpleStringProperty("");

    public DataProjektyPracownika(String nazwa, String head, String status, String termin) {
        setProjektyPracownika_nazwa(nazwa);
        setProjektyPracownika_head(head);
        setProjektyPracownika_status(status);
        setProjektyPracownika_termin(termin);
    }
    public DataProjektyPracownika() {
        this( "", "", "","");
    }
    public String getProjektyPracownika_nazwa() {
        return projektyPracownika_nazwaProperty.get();
    }
    public void setProjektyPracownika_nazwa(String nazwa) {
        projektyPracownika_nazwaProperty.set(nazwa);
    }
    public String getProjektyPracownika_head() {
        return projektyPracownika_headProperty.get();
    }
    public void setProjektyPracownika_head(String head) {
        projektyPracownika_headProperty.set(head);
    }
    public String getProjektyPracownika_status() {
        return projektyPracownika_statusProperty.get();
    }
    public void setProjektyPracownika_status(String status) {
        projektyPracownika_statusProperty.set(status);
    }
    public String getProjektyPracownika_termin() {
        return projektyPracownika_terminProperty.get();
    }
    public void setProjektyPracownika_termin(String termin) {
        projektyPracownika_terminProperty.set(termin);
    }


}
