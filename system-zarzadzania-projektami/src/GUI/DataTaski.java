package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataTaski {

    private final SimpleIntegerProperty taskiTable_idProperty = new SimpleIntegerProperty(0);
    private final SimpleStringProperty taskiTable_nazwaProperty = new SimpleStringProperty("");
    private final SimpleStringProperty taskiTable_pracownikProperty = new SimpleStringProperty("");
    private final SimpleStringProperty taskiTable_progressProperty = new SimpleStringProperty("");
    private final SimpleStringProperty taskiTable_terminProperty = new SimpleStringProperty("");

    public DataTaski(Integer id, String nazwa, String pracownik, String progress, String termin) {
        setTaskiTable_id(id);
        setTaskiTable_nazwa(nazwa);
        setTaskiTable_pracownik(nazwa);
        setTaskiTable_progress(nazwa);
        setTaskiTable_termin(nazwa);
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

    public void setTaskiTable_pracownik(String nazwa) { taskiTable_pracownikProperty.set(nazwa); }

    public String getTaskiTable_progress() { return taskiTable_progressProperty.get(); }

    public void setTaskiTable_progress(String nazwa) { taskiTable_progressProperty.set(nazwa); }

    public String getTaskiTable_termin() { return taskiTable_terminProperty.get(); }

    public void setTaskiTable_termin(String nazwa) { taskiTable_terminProperty.set(nazwa); }
}
