package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataTaski {

    private final SimpleIntegerProperty taskiTable_idProperty = new SimpleIntegerProperty(0);
    private final SimpleStringProperty taskiTable_nazwaProperty = new SimpleStringProperty("");

    public DataTaski(Integer id, String nazwa) {
        setTaskiTable_id(id);
        setTaskiTable_nazwa(nazwa);
    }

    public DataTaski() {
        this(0, "");
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

}
