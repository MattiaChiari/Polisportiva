package it.unife.lp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.unife.lp.model.Attivita;
import it.unife.lp.model.user; 
import it.unife.lp.view.ActivityEditDialog;
import it.unife.lp.view.ActivityOverviewController;
import it.unife.lp.view.IscriptionDialog;
import it.unife.lp.view.PersonEditDialogController;
import it.unife.lp.view.PersonOverviewController;
import it.unife.lp.view.RootLayoutController;
import it.unife.lp.view.StatisticsContorller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import java.util.prefs.Preferences;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class MainApp extends Application {

    private ObservableList<user> personData= FXCollections.observableArrayList();
    private ObservableList<Attivita> ActivityData= FXCollections.observableArrayList();

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Attivita AttivitaDiAppoggio;

    public MainApp(){
        //Riempimento utenti
        personData.add(new user("Mario","Rossi"));
        personData.add(new user("Luca","Rizzoli"));
        personData.add(new user("Anna","Rigosi"));
        personData.add(new user("Giorgio","Lucchi"));
        personData.add(new user("Asia","Namasco"));
        personData.add(new user("Ion","Amisano"));
        

        for(user u:personData){
            Attivita a1 = new Attivita("Calcio", "11 perrsone inseguono un pallone"," "," "," ");
            Attivita a2 = new Attivita("Basket", "5 persone rimbalzano un pallone"," "," "," ");
            u.AddAttivitàPrefe(a1);
            u.AddAttivitàPrefe(a2);
        }

        //Riempimento attività
        ActivityData.add(new Attivita("Pallavolo","6 Persone schiacciano la palla per fare punto","Martedi", "13", "16"));
        ActivityData.add(new Attivita("Pallavolo","6 Persone schiacciano la palla per fare punto","Mercoledì", "9", "11"));
        ActivityData.add(new Attivita("Calcio","11 perrsone inseguono un pallone","Giovedi", "16:30", "20:30"));
        ActivityData.add(new Attivita("Basket","5 persone rimbalzano un pallone","Venerdi", "9", "12"));
        ActivityData.add(new Attivita("Calcio","11 perrsone inseguono un pallone","Giovedi", "16:30", "20:30"));
        ActivityData.add(new Attivita("Calcio","11 perrsone inseguono un pallone","Sabato", "11:30", "13:30"));
        ActivityData.add(new Attivita("Basket","5 persone rimbalzano un pallone","Venerdi", "9", "12"));
        ActivityData.add(new Attivita("Pallavolo","6 Persone schiacciano la palla per fare punto","Sabato", "9", "11"));

        for(user u:personData){
            u.AddIscrizione(new Attivita("Calcio", " ", "Giovedi", "16:30", "20:30"));
        }
    }
    
    public ObservableList<user> getPersonData() {
        return personData;
    }

    public ObservableList<Attivita> getActivityData(){
        return ActivityData;
    }

    public ObservableList<Attivita> getIscr(user u){
        for(user p:personData){
            if(p.getFirstName().equals(u.getFirstName())){
                return u.getIscrizioni();
            }
        }
        return null;
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setWidth(600);  // Queste righe sovrascrivono la dimensione prefissata
        primaryStage.setHeight(700);

        this.primaryStage.setTitle("PolisportivApp");
        initRootLayout();
        showPersonOverview();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
            .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    //Gestione del EditDialog Atleti
    public boolean showPersonEditDialog(user person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifica Atleta");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    //Gestione Lista attività
    public void showActivities() {
        try {
            // Caricamento del file FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ActivityOverview.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
    
            // Imposta il layout centrale con il contenuto della nuova pagina
            rootLayout.setCenter(page);
    
            // Ottieni il controller e passagli i dati
            ActivityOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Gestione del EditDialog Attività
    public boolean showActivityEditDialog(Attivita a) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ActivityEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifica Attività");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ActivityEditDialog controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setActivity(a);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Gestione Iscrizioni
    public boolean showIscriptionDialog(Attivita a) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/IscriptionDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Iscrivi Atleta");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            IscriptionDialog controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            //controller.setActivity(a);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void checkIscrizione (Attivita a) {
        AttivitaDiAppoggio = a;
    }
    
    public void addSubscription(String firstName, String lastName) {
        user selectedUser = personData.stream()
                                  .filter(u -> u.getFirstName().equals(firstName) && u.getLastName().equals(lastName))
                                  .findFirst()
                                  .orElse(null);
        
        if (selectedUser != null) {
            // Aggiungi l'attività selezionata alla lista delle iscrizioni dell'utente
            if (AttivitaDiAppoggio != null) {
                selectedUser.AddIscrizione(AttivitaDiAppoggio);
            }
        }
    }


    
    //Gestione filepath utenti
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("personFilePath", null); 
        System.out.println("Percorso salvato per utenti: " + filePath); // DEBUG
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("personFilePath", file.getPath()); 
            primaryStage.setTitle("AddressApp - Utenti: " + file.getName());
        } else {
            prefs.remove("personFilePath");
            primaryStage.setTitle("AddressApp");
        }
    }

    //Gestione filepath attività
    public File getActivityFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("activityFilePath", null); 
        System.out.println("Percorso salvato per attività: " + filePath); // DEBUG
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setActivityFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("activityFilePath", file.getPath()); // Usa una chiave distinta
            primaryStage.setTitle("AddressApp - Attività: " + file.getName());
        } else {
            prefs.remove("activityFilePath");
            primaryStage.setTitle("AddressApp");
        }
    }


    //Caricamento dati delle persone**
    public void loadPersonDataFromFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());        //Aggiungereeeee
            List<user> users = Arrays.asList(mapper.readValue(file, user[].class));
    
            // Dopo aver deserializzato, aggiorniamo le ObservableList
            for (user user : users) {
                // Usiamo i metodi per aggiornare le ObservableList
                user.setAttivitaPreferite(user.getAttivitaPreferiteList());  // Converte la List in ObservableList
                user.setIscrizioni(user.getIscrizioniList());  // Converte la List in ObservableList
            }
    
            personData.clear();
            personData.addAll(users);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Impossibile caricare i dati");
            alert.setContentText("Dati non trovati nel file:\n" + file.getPath());
            alert.showAndWait();
        }
    }


    

    //Salvataggio dati delle persone
    public void savePersonDataToFile(File file) {
        try {
            System.out.println("Salvataggio file utenti in: " + file.getAbsolutePath()); // DEBUG
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(file, personData);
            setPersonFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Impossibile salvare i dati");
            alert.setContentText("Dati non trovati nel file:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    //Caricamento dati delle attivita

    public void loadActivityDataFromFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            List<Attivita> loadedActivities = mapper.readValue(file, new TypeReference<List<Attivita>>() {});
            ActivityData.setAll(FXCollections.observableArrayList(loadedActivities));
            setActivityFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Impossibile caricare i dati");
            alert.setContentText("Dati non trovati nel file:\n" + file.getPath());
            alert.showAndWait();
        }
    }


    
        
    //Salvataggio dati delle attivita
    public void saveActivityDataToFile(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(file, ActivityData);
            setActivityFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Impossibile salvare i dati");
            alert.setContentText("Dati non trovati nel file:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    
    //Grafico
    public void showStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RegistrationsStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Statistica Iscrizioni");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            StatisticsContorller controller = loader.getController();
            controller.setStatistics(this);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}