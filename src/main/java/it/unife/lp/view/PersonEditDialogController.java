package it.unife.lp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import it.unife.lp.model.user;
import it.unife.lp.util.DateUtil;
import javafx.scene.control.CheckBox;


public class PersonEditDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField emaiField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField birthdayField;
    //private TextField AttivitaPrefeField;
    @FXML
    private CheckBox volleyballCheckBox;
    @FXML
    private CheckBox soccerCheckBox;
    @FXML
    private CheckBox basketballCheckBox;

    private Stage dialogStage;
    private user person;
    private boolean okClicked = false;
    
    @FXML
    private void initialize() {
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setPerson(user person) {
        this.person = person;
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        phoneNumberField.setText(Integer.toString(person.getPhoneNumber()));
        emaiField.setText(person.getEmail());
        birthdayField.setText(DateUtil.format(person.getComple()));
        birthdayField.setPromptText("dd.mm.yyyy");
        //Attività prefe
        volleyballCheckBox.setSelected(person.checkVolley());
        soccerCheckBox.setSelected(person.checkSoccer());
        basketballCheckBox.setSelected(person.checkBasket());

        volleyballCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            person.setVolleyballPreferred(newValue);
        });
    
        soccerCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            person.setSoccerPreferred(newValue);
        });
    
        basketballCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            person.setBasketPreferred(newValue);
        });
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPhoneNumber(Integer.parseInt(phoneNumberField.getText()));
            person.setEmail(emaiField.getText());
            person.setComple(DateUtil.parse(birthdayField.getText()));
            //Attività prefe
            person.azzeraLista();
            person.setBasketPreferred(basketballCheckBox.isSelected());
            person.setVolleyballPreferred(volleyballCheckBox.isSelected());
            person.setSoccerPreferred(soccerCheckBox.isSelected());

            okClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Nome non valido\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Cognome non valido\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "Indirizzo non valido\n";
        }
        if (phoneNumberField.getText() == null || phoneNumberField.getText().length() == 0) {
            errorMessage += "Numero di telefono non valido\n";
        } else {
            //Controllo che il numero non abbia lettere
            try {
                Integer.parseInt(phoneNumberField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "NUmero di telefono non valido (solo numeri)\n";
            }
        }
        if (emaiField.getText() == null || emaiField.getText().length() == 0) {
            errorMessage += "Emial non valida!\n";
        }
        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Data di nascita non valida\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "Data di nascita non valida. Usa il formato dd.mm.yyyy!\n";
            }
        }
        
        if(!volleyballCheckBox.isSelected() && !soccerCheckBox.isSelected() && !basketballCheckBox.isSelected()) {
            errorMessage += "Seleziona almeno un'attività preferita!\n";
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

