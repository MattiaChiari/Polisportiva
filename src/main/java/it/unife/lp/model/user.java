package it.unife.lp.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class user {
    private final StringProperty nome;
    private final StringProperty cognome;
    private final ObjectProperty<LocalDate> DatadiNascita;
    private final StringProperty indirizzo;
    private final IntegerProperty telefono;
    private final StringProperty email;
    
    private ObservableList<Attivita> attivitaPreferite;
    
    private ObservableList<Attivita> iscrizioni;

    @JsonProperty("attivitaPreferite")
    private List<Attivita> attivitaPreferiteList;

    @JsonProperty("iscrizioni")
    private List<Attivita> iscrizioniList;

    @JsonIgnore
    private boolean volleyballPreferred = false;
    @JsonIgnore
    private boolean soccerPreferred = true;
    @JsonIgnore
    private boolean basketballPreferred = true;

    
    public user() {
        this(null, null);
    }

    /**
    * Constructor with some initial data.
    *
    * @param nome
    * @param cognome
    */
    public user(String nome, String cognome) {
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.indirizzo = new SimpleStringProperty("some street");
        this.telefono = new SimpleIntegerProperty(1234567890);
        this.email = new SimpleStringProperty("user.user@gmail.com");
        this.DatadiNascita = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        this.attivitaPreferite = FXCollections.observableArrayList();
        this.iscrizioni = FXCollections.observableArrayList();
        this.attivitaPreferiteList = new ArrayList<>(attivitaPreferite); // Sincronizza la lista normale
        this.iscrizioniList = new ArrayList<>();
    }

    // Metodi per sincronizzare ObservableList e List normale
    public List<Attivita> getAttivitaPreferite() {
        attivitaPreferiteList = new ArrayList<>(attivitaPreferite); // Assicura che attivitaPreferiteList venga aggiornata
        return attivitaPreferiteList;
    }

    //Per salvare nel Json una lista e non una ObserverList
    @JsonProperty("attivitaPreferite")
    public List<Attivita> getAttivitaPreferiteList() {
        return new ArrayList<>(attivitaPreferite);  // Converte ObservableList in List per Jackson
    }
    @JsonProperty("iscrizioni")
    public List<Attivita> getIscrizioniList() {
        return new ArrayList<>(iscrizioni);  // Converte ObservableList in List per Jackson
    }

    public void setAttivitaPreferite(List<Attivita> attivitaList) {
        this.attivitaPreferite = FXCollections.observableArrayList(attivitaList);  // Converte List in ObservableList
    }
    public void setIscrizioni(List<Attivita> iscrizioniList) {
        this.iscrizioni = FXCollections.observableArrayList(iscrizioniList);  // Converte List in ObservableList
    }

    public ObservableList<Attivita> getIscrizioni() {
        return iscrizioni;
    }

    

    // Metodi ObservableList (interni all'applicazione)
    public void addAttivitaPreferita(Attivita a) {
        this.attivitaPreferite.add(a);
    }

    public void addIscrizione(Attivita a) {
        this.iscrizioni.add(a);
    }

    //Gestione Iscrizioni
    public void AddIscrizione(Attivita a){
        this.iscrizioni.add(a);
    }

    public void RemoveIscrizione(Attivita a){
        this.iscrizioni.remove(a);
    }

    //Gestione Nome
    public String getFirstName() {
        return nome.get();
    }
    public void setFirstName(String nome) {
        this.nome.set(nome);
    }
    public StringProperty firstNameProperty() {
        return nome;
    }


    //Gestione Conogme
    public String getLastName() {
        return cognome.get();
    }
    public void setLastName(String lastName) {
        this.cognome.set(lastName);
    }
    public StringProperty lastNameProperty() {
        return cognome;
    }
    //indirizzo
    
    public String getStreet() {
        return indirizzo.get();
    }
    public void setStreet(String street) {
        this.indirizzo.set(street);
    }
    public StringProperty streetProperty() {
        return indirizzo;
    }
    //Gestione numero di telefono
    public int getPhoneNumber() {
        return telefono.get();
    }
    public void setPhoneNumber(int tel) {
        this.telefono.set(tel);
    }
    public IntegerProperty PhoneNumberProperty() {
        return telefono;
    }
    //Gestione Email
    public String getEmail() {
        return email.get();
    }
    public void setEmail(String em) {
        this.email.set(em);
    }
    public StringProperty EmialProperty() {
        return email;
    }
    //gestione Data di nascità
    public LocalDate getComple() {
        return DatadiNascita.get();
    }
    public void setComple(LocalDate comple) {
        this.DatadiNascita.set(comple);
    }
    public ObjectProperty<LocalDate> CompleProperty() {
        return DatadiNascita;
    }
    //Gestione attività
    public void AddAttivitàPrefe(Attivita a) {
        this.attivitaPreferite.add(a);
        this.attivitaPreferiteList = new ArrayList<>(attivitaPreferite); // Sincronizza la lista normale
    }

    public void RemoveAttivitàPrefe(Attivita a) {
        this.attivitaPreferite.remove(a);
        this.attivitaPreferiteList = new ArrayList<>(attivitaPreferite); // Sincronizza la lista normale
    }

    public String toStringAttività(){
        String tmp="";
        for(Attivita a :attivitaPreferite){
            tmp=tmp+a.getNameActivity()+", ";
        }
        tmp=tmp.substring(0, tmp.length() - 2);
        return tmp;
    }

    public void azzeraLista(){
        attivitaPreferite.clear();
    }

    public Boolean checkVolley(){
        return volleyballPreferred;
    }

    public void setVolleyballPreferred(boolean volleyballPreferred) {
        this.volleyballPreferred = volleyballPreferred;
        if (volleyballPreferred) {
            AddAttivitàPrefe(new Attivita("Pallavolo", " ", " ", " ", " "));
        } else {
            RemoveAttivitàPrefe(new Attivita("Pallavolo", " ", " ", " ", " "));
        }
    }

    public Boolean checkSoccer(){
        return soccerPreferred;
    }

    public void setSoccerPreferred(boolean soccerPreferred) {
        this.soccerPreferred = soccerPreferred;
        if (soccerPreferred) {
            // Aggiungi l'attività Calcio alla lista delle preferenze
            AddAttivitàPrefe(new Attivita("Calcio", " ", " ", " ", " "));
        } else {
            // Rimuovi l'attività Calcio dalla lista delle preferenze
            RemoveAttivitàPrefe(new Attivita("Calcio", " ", " ", " ", " "));
        }
    }

    public Boolean checkBasket(){
        return basketballPreferred;
    }

    public void setBasketPreferred(boolean basketballPreferred) {
        this.basketballPreferred = basketballPreferred;
        if (basketballPreferred) {
            // Aggiungi l'attività Basket alla lista delle preferenze
            AddAttivitàPrefe(new Attivita("Basket", " ", " ", " ", " "));
        } else {
            // Rimuovi l'attività Basket dalla lista delle preferenze
            RemoveAttivitàPrefe(new Attivita("Basket", " ", " ", " ", " "));
        }
    }
}
