package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Klasa zawierająca informacje o pracownikach
 */
public class DataPracownicy {

    private final SimpleIntegerProperty pracownicyTable_idProperty = new SimpleIntegerProperty(0);
    private final SimpleStringProperty pracownicyTable_imieProperty = new SimpleStringProperty("");
    private final SimpleStringProperty pracownicyTable_nazwiskoProperty = new SimpleStringProperty("");
    private final SimpleStringProperty pracownicyTable_stanowiskoProperty = new SimpleStringProperty("");

    public DataPracownicy(Integer id, String imie, String nazwisko, String dostep) {
        setPracownicyTable_id(id);
        setPracownicyTable_imie(imie);
        setPracownicyTable_nazwisko(nazwisko);
        setPracownicyTable_stanowisko(dostep);
    }

    public DataPracownicy() {
        this(0, "", "", "");
    }

    public Integer getPracownicyTable_id() {
        return pracownicyTable_idProperty.get();
    }

    public void setPracownicyTable_id(Integer id) {
        pracownicyTable_idProperty.set(id);
    }

    public String getPracownicyTable_imie() {
        return pracownicyTable_imieProperty.get();
    }

    public void setPracownicyTable_imie(String imie) {
        pracownicyTable_imieProperty.set(imie);
    }

    public String getPracownicyTable_nazwisko() {
        return pracownicyTable_nazwiskoProperty.get();
    }

    public void setPracownicyTable_nazwisko(String nazwisko) {
        pracownicyTable_nazwiskoProperty.set(nazwisko);
    }

    public String getPracownicyTable_stanowisko() {
        return pracownicyTable_stanowiskoProperty.get();
    }

    public void setPracownicyTable_stanowisko(String dostep) {
        pracownicyTable_stanowiskoProperty.set(dostep);
    }
}
