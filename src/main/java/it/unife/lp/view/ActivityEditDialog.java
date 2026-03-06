package it.unife.lp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import it.unife.lp.model.Attivita;
import it.unife.lp.model.user;
import it.unife.lp.util.DateUtil;

public class ActivityEditDialog {
    @FXML
    private TextField NameField;
    @FXML
    private TextField DescriprtionField;
    @FXML
    private TextField DayField;
    @FXML
    private TextField OraInizioField;
    @FXML
    private TextField OraFineField;

    private Stage dialogStage;
    private Attivita a;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    //Set attivita
    public void setActivity(Attivita a) {
        this.a = a;
        NameField.setText(a.getNameActivity());
        DescriprtionField.setText(a.getDescription());
        DayField.setText(a.getDay());
        OraInizioField.setText(a.getOraInizio());
        OraInizioField.setText(a.getOraFine());
    }

    //OK
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            a.setNameActivity(NameField.getText());
            a.setDescription(DescriprtionField.getText());
            a.setDay(DayField.getText());
            a.setOraInizio(OraInizioField.getText());
            a.setOraFine(OraFineField.getText());
            
            okClicked = true;
            dialogStage.close();
        }
    }

    //Cancel
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        if (NameField.getText() == null || NameField.getText().length() == 0) {
            errorMessage += "Nome non valido\n";
        }
        if (DescriprtionField.getText() == null || DescriprtionField.getText().length() == 0) {
            errorMessage += "Descrizione non valida\n";
        }
        if (DayField.getText() == null || DayField.getText().length() == 0) {
            errorMessage += "Giorno non valido\n";
        }
        
        if (OraInizioField.getText() == null || OraInizioField.getText().length() == 0) {
            errorMessage += "Ora di inizio non valida!\n";
        }

        if (OraFineField.getText() == null || OraFineField.getText().length() == 0) {
            errorMessage += "Ora di fine non valida!\n";
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

}
