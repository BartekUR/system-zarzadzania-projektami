package GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Michal on 2017-05-09.
 */
public class DataMojeProjekty {
    private final SimpleIntegerProperty idMProjektyProperty = new SimpleIntegerProperty(0);
    private final SimpleStringProperty taskMProjektyProperty = new SimpleStringProperty("");
    private final SimpleStringProperty pracownikMProjektyProperty = new SimpleStringProperty("");
    private final SimpleStringProperty progressMProjektyProperty = new SimpleStringProperty("");
    private final SimpleStringProperty terminMProjektyProperty = new SimpleStringProperty("");

    public DataMojeProjekty(Integer id_projekt, String task, String pracownik, String progress, String termin) {
        setidMProjekty(id_projekt);
        settaskMProjekty(task);
        setpracownikMProjekty(pracownik);
        setprogressMProjekty(progress);
        setterminMProjekty(termin);
    }

    public DataMojeProjekty() {this(0, "", "", "",""); }

    public Integer setidMProjekty(int id_projekt) {
        return idMProjektyProperty.get();
    }

    public void  setidMProjekty(Integer id_projekt) { idMProjektyProperty.set(id_projekt); }

    public String settaskMProjekty() {
        return taskMProjektyProperty.get();
    }

    public void settaskMProjekty(String task) {taskMProjektyProperty.set(task); }

    public String setpracownikMProjekty() {
        return pracownikMProjektyProperty.get();
    }

    public void setpracownikMProjekty(String pracownik) {
        pracownikMProjektyProperty.set(pracownik);
    }

    public String setprogressMProjekty() {
        return progressMProjektyProperty.get();
    }

    public void setprogressMProjekty(String progress) {
        progressMProjektyProperty.set(progress);
    }

    public String setterminMProjekty() {
        return terminMProjektyProperty.get();
    }

    public void setterminMProjekty(String termin) {
        terminMProjektyProperty.set(termin);
    }


}
