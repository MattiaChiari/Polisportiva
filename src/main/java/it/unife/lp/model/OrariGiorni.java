package it.unife.lp.model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrariGiorni {
    private final StringProperty giorno;
    private final StringProperty oraInizio;
    private final StringProperty oraFine;

    public OrariGiorni(String giorno, String inizio, String fine) {
        this.giorno = new SimpleStringProperty(giorno);
        this.oraInizio = new SimpleStringProperty(inizio);
        this.oraFine = new SimpleStringProperty(fine);
    }

    //gestione Giorno
    public String getDay() {
        return giorno.get();
    }
    public void setDay(String giorno) {
        this.giorno.set(giorno);
    }
    public StringProperty dayProperty() {
        return giorno;
    }

    //gestione Ora Inizio
    public String getOraInizio() {
        return oraInizio.get();
    }
    public void setOraInizio(String oraInizio ) {
        this.oraInizio.set(oraInizio);
    }
    public StringProperty OraInizioProperty() {
        return oraInizio;
    }

    //gestione Ora Fine
    public String getoraFine() {
        return oraFine.get();
    }
    public void setoraFine(String oraFine ) {
        this.oraFine.set(oraFine);
    }
    public StringProperty oraFineProperty() {
        return oraFine;
    }
    
}
