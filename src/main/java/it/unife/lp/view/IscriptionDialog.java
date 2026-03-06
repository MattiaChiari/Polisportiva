package it.unife.lp.view;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import it.unife.lp.MainApp;
import javafx.stage.Stage;
import it.unife.lp.model.Attivita;
import it.unife.lp.model.user;
import it.unife.lp.util.DateUtil;

public class IscriptionDialog {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;


    private Stage dialogStage;
    private boolean okClicked = false;
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public String getFirstName() {
        return firstNameField.getText();
    }
    
    public String getLastName() {
        return lastNameField.getText();
    }

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;        
    }

    @FXML
    private void handleOk() {
        String nome = firstNameField.getText();
        String conome = lastNameField.getText();

        if (isInputValid()) {
            mainApp.addSubscription(nome, conome);      
            okClicked = true;
            dialogStage.close();
        }else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText("Nessun elemento selezionato");
            alert.setContentText("Seleziona un elemento dela tabella per poterlo eliminare.");
            alert.showAndWait();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Nome non valido\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Descrizione non valida\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Inserimento non valido");
            alert.setHeaderText("Controlla i campi errati");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
}
