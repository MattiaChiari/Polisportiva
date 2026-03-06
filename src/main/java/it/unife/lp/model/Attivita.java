package it.unife.lp.model;

import java.time.LocalDate;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Attivita {
    private final StringProperty nomeAttivita;
    //private ObservableList<OrariGiorni> listaG= FXCollections.observableArrayList();
    private final StringProperty descrizione;
    private final StringProperty giorno;
    private final StringProperty oraInizio;
    private final StringProperty oraFine;

    public Attivita() {
        this.nomeAttivita = new SimpleStringProperty();
        this.descrizione = new SimpleStringProperty();
        this.giorno = new SimpleStringProperty();
        this.oraInizio = new SimpleStringProperty();
        this.oraFine = new SimpleStringProperty();
    }

    public Attivita(String nome,String desc,String giorno,String oraIniz,String oraFin) {
        this.nomeAttivita = new SimpleStringProperty(nome);
        this.descrizione = new SimpleStringProperty(desc);
        this.giorno = new SimpleStringProperty(giorno);
        this.oraInizio = new SimpleStringProperty(oraIniz);
        this.oraFine = new SimpleStringProperty(oraFin);

        //this.listaG = FXCollections.observableArrayList();
    }

    //Gestione Nome
    public String getNameActivity() {
        return nomeAttivita.get();
    }
    public void setNameActivity(String nome) {
        this.nomeAttivita.set(nome);
    }
    public StringProperty nameActivityroperty() {
        return nomeAttivita;
    }

    //Gestione Orari
    //public void AddActivity(String giorno,String oraIniz,String oraFin){
        //listaG.add(new OrariGiorni(giorno,oraIniz,oraFin));
        //this.giorno=new SimpleStringProperty(giorno);
    //}
    /* 
    public String toStringDay(){
        String tmp="";
        for(OrariGiorni o:listaG){
            tmp=tmp+o.getDay()+", ";
        }
        tmp=tmp.substring(0, tmp.length() - 2);
        return tmp;
    }
    */
    
    //Gestione Descrizione
    public String getDescription() {
        return descrizione.get();
    }
    public void setDescription(String street) {
        this.descrizione.set(street);
    }
    public StringProperty descriptionProperty() {
        return descrizione;
    }

    //Gestione Giorno
    public String getDay() {
        return giorno.get();
    }
    public void setDay(String day) {
        this.giorno.set(day);
    }
    public StringProperty dayProperty() {
        return giorno;
    }

    //Gestione OraInizio
    public String getOraInizio() {
        return oraInizio.get();
    }
    public void setOraInizio(String g) {
        this.oraInizio.set(g);
    }
    public StringProperty oraInizioProperty() {
        return oraInizio;
    }

    //Gestione OraFine
    public String getOraFine() {
        return oraFine.get();
    }
    public void setOraFine(String g) {
        this.oraFine.set(g);
    }
    public StringProperty oraFineProperty() {
        return oraFine;
    }
    
}
