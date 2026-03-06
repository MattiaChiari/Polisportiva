package it.unife.lp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import it.unife.lp.MainApp;
import it.unife.lp.model.Attivita;
import it.unife.lp.model.user;
import it.unife.lp.util.DateUtil;

public class ActivityOverviewController {
    @FXML
    private TableView<Attivita> activityTable;
    @FXML
    private TableColumn<Attivita, String> nomeColumn;
    @FXML
    private TableColumn<Attivita, String> descrizioneColumn;
    @FXML
    private TableColumn<Attivita, String> dayColumn;
    @FXML
    private TableColumn<Attivita, String> oraInizioColumn;
    @FXML
    private TableColumn<Attivita, String> oraFineColumn;

    private MainApp mainApp;
    private Stage dialogStage;



    public ActivityOverviewController() {
    }
    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nameActivityroperty());
        descrizioneColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        dayColumn.setCellValueFactory(cellData -> cellData.getValue().dayProperty());
        oraInizioColumn.setCellValueFactory(cellData -> cellData.getValue().oraInizioProperty());
        oraFineColumn.setCellValueFactory(cellData -> cellData.getValue().oraFineProperty());

        showActivityDetails(null);
        activityTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showActivityDetails(newValue));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        activityTable.setItems(mainApp.getActivityData());
    }

    
    private void showActivityDetails(Attivita a) {
        if (a != null) {
            nomeColumn.setText(a.getNameActivity());
            descrizioneColumn.setText(a.getDescription());
            dayColumn.setText(a.getDay());
            oraInizioColumn.setText(a.getOraInizio());
            oraFineColumn.setText(a.getOraFine());
        } else {
            nomeColumn.setText("Nome");
            descrizioneColumn.setText("Descrizione");
            dayColumn.setText("Giorno");
            oraInizioColumn.setText("OraInizio");
            oraFineColumn.setText("oraInizio");
        }
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // Pulsante elimina
    @FXML
    private void handleDeleteActivity() {
        int selectedIndex = activityTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            activityTable.getItems().remove(selectedIndex);
        } else {
        // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText("Nessun elemento selezionato");
            alert.setContentText("Seleziona un elemento dela tabella per poterlo eliminare.");
            alert.showAndWait();
        }
    }

    //Pulsante nuova attività
    @FXML
    private void handleNewActivity() {
        Attivita a = new Attivita(null, null, null, null, null);
        boolean okClicked = mainApp.showActivityEditDialog(a); 
        if (okClicked) {                                
            mainApp.getActivityData().add(a);        
        }
    }

    //Pulsante Edit Attivita
    @FXML
    private void handleEditActiviti() {
        Attivita a = activityTable.getSelectionModel().getSelectedItem();
        if (a != null) {
            boolean okClicked = mainApp.showActivityEditDialog(a);
        if (okClicked) {
            showActivityDetails(a);
        }
        } else {
            // Nessuna selezione
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nessuna Selezione");
            alert.setHeaderText("Non hai selezionato la persona da modificare");
            alert.setContentText("Scegli una persona della tabella.");
            alert.showAndWait();
        }
    }

    //Pulsante Utenti
    @FXML
    private void handleUsers() {
        mainApp.showPersonOverview();
    }

    //pulsante Iscrivi
    @FXML
    private void handleIscription() {
        Attivita a = activityTable.getSelectionModel().getSelectedItem();
        if (a != null) {
            mainApp.checkIscrizione(a);
            boolean okClicked = mainApp.showIscriptionDialog(a);
            
        } else {
            // Nessuna selezione
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nessuna Selezione");
            alert.setHeaderText("Non hai selezionato una attivita a cui iscrivere");
            alert.setContentText("Scegli una attività della tabella.");
            alert.showAndWait();
        }
    }
    
}
