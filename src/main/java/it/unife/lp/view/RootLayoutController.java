package it.unife.lp.view;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import it.unife.lp.MainApp;

    public class RootLayoutController {
    private MainApp mainApp;
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    //New file users
    @FXML
    private void handleNewUsers() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
        
    }

    //New file Activity
    @FXML
    private void handleNewActivity() {
        mainApp.getActivityData().clear();
        mainApp.setActivityFilePath(null);
        
    }

    //Apertura file utenti +
    @FXML
    private void handleOpenUsers() {
        // Usa il FileChooser per aprire il file
        FileChooser fileChooser = configureJsonFileChooser("Apri file utenti");
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        
        if (file != null) {
            mainApp.loadPersonDataFromFile(file);  // Carica i dati dal file selezionato
        }
    }

    //Apertura file utenti+
    @FXML
    private void handleOpenActivity() {
        // Usa il FileChooser per aprire il file
        FileChooser fileChooser = configureJsonFileChooser("Apri file attività");
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        
        if (file != null) {
            mainApp.loadActivityDataFromFile(file);  // Carica i dati dal file selezionato
        }
    }

    //Save Users+
    @FXML
    private void handleSaveUsers() {
        File personFile = mainApp.getPersonFilePath();
        
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);  // Salva i dati nel file
        } else {
            handleSaveAsUsers();  // Se non c'è un file esistente, chiedi all'utente dove salvare
        }
    }

    //Save Activity+
    @FXML
    private void handleSaveActivity() {
        File activityFile = mainApp.getActivityFilePath();
        
        if (activityFile != null) {
            mainApp.saveActivityDataToFile(activityFile);  // Salva i dati nel file
        } else {
            handleSaveAsActivity();  // Se non c'è un file esistente, chiedi all'utente dove salvare
        }
    }

    //Save as Users
    @FXML
    private void handleSaveAsUsers() {
        // Usa il FileChooser per scegliere il file di salvataggio
        FileChooser fileChooser = configureJsonFileChooser("Salva file utenti come");
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        
        if (file != null) {
            if (!file.getPath().endsWith(".json")) {
                file = new File(file.getPath() + ".json");  // Aggiungi l'estensione .json se non presente
            }
            mainApp.savePersonDataToFile(file);  // Salva i dati nel file
        }
    }

    //Save as Activity
    @FXML
    private void handleSaveAsActivity() {
        // Usa il FileChooser per scegliere il file di salvataggio
        FileChooser fileChooser = configureJsonFileChooser("Salva file attività come");
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        
        if (file != null) {
            if (!file.getPath().endsWith(".json")) {
                file = new File(file.getPath() + ".json");  // Aggiungi l'estensione .json se non presente
            }
            mainApp.saveActivityDataToFile(file);  // Salva i dati nel file
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mattia Chiariello");
        alert.setHeaderText("About");
        alert.setContentText("L'autore di questa applicazione è Mattia Chiariello");
        alert.showAndWait();
    }
    
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleShowStatistics() {
        mainApp.showStatistics();
    }

    private FileChooser configureJsonFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        
        // Filtro per file JSON
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        
        return fileChooser;
    }
}
