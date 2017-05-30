package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataTaski {

    private final SimpleIntegerProperty taskiTable_idProperty = new SimpleIntegerProperty(0);
    private final SimpleStringProperty taskiTable_nazwaProperty = new SimpleStringProperty("");
    private final SimpleStringProperty taskiTable_pracownikProperty = new SimpleStringProperty("");
    private final SimpleStringProperty taskiTable_statusProperty = new SimpleStringProperty("");
    private final SimpleStringProperty taskiTable_terminProperty = new SimpleStringProperty("");

    public DataTaski(Integer id, String nazwa, String pracownik, String status, String termin) {
        setTaskiTable_id(id);
        setTaskiTable_nazwa(nazwa);
        setTaskiTable_pracownik(pracownik);
        setTaskiTable_status(status);
        setTaskiTable_termin(termin);
    }

    public DataTaski() {
        this(0, "", "", "", "");
    }

    public Integer getTaskiTable_id() {
        return taskiTable_idProperty.get();
    }

    public void setTaskiTable_id(Integer id) {
        taskiTable_idProperty.set(id);
    }

    public String getTaskiTable_nazwa() {
        return taskiTable_nazwaProperty.get();
    }

    public void setTaskiTable_nazwa(String nazwa) {
        taskiTable_nazwaProperty.set(nazwa);
    }

    public String getTaskiTable_pracownik() { return taskiTable_pracownikProperty.get(); }

    public void setTaskiTable_pracownik(String pracownik) { taskiTable_pracownikProperty.set(pracownik); }

    public String getTaskiTable_status() { return taskiTable_statusProperty.get(); }

    public void setTaskiTable_status(String status) { taskiTable_statusProperty.set(status); }

    public String getTaskiTable_termin() { return taskiTable_terminProperty.get(); }

    public void setTaskiTable_termin(String termin) { taskiTable_terminProperty.set(termin); }
}
