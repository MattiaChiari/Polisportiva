package it.unife.lp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import it.unife.lp.MainApp;
import it.unife.lp.model.Attivita;
import it.unife.lp.model.user;
import it.unife.lp.util.DateUtil;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PersonOverviewController {
    @FXML
    private TableView<user> personTable;
    @FXML
    private TableView<Attivita> IscrittedPerson;
    @FXML
    private TableColumn<user, String> firstNameColumn;
    @FXML
    private TableColumn<user, String> lastNameColumn;
    @FXML
    private TableColumn<Attivita, String> NameColumn;
    @FXML
    private TableColumn<Attivita, String> dayColumn;
    @FXML
    private TableColumn<Attivita, String> oraInizioColumn;
    @FXML
    private TableColumn<Attivita, String> oraFineColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label emaiLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label AttivitaPrefe;
    @FXML
    private ImageView athleteImageView;


    private MainApp mainApp;

    public PersonOverviewController() {
    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        Image athleteImage = new Image(getClass().getResourceAsStream("/it/unife/lp/Image/user-icon.png"));
        athleteImageView.setImage(athleteImage);
        athleteImageView.setFitWidth(100);  // Impostare la larghezza desiderata per l'immagine
        athleteImageView.setFitHeight(100); // Impostare l'altezza desiderata per l'immagine
        athleteImageView.setPreserveRatio(true); // Mantieni il rapporto di aspetto
        athleteImageView.setVisible(false);     //se non cè selezione l'immagine non deve esserci
        showPersonDetails(null);

        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showPersonDetails(newValue);
            updateImage(newValue); // Aggiungi questa riga per aggiornare l'immagine
        });
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));

        NameColumn.setCellValueFactory(cellData -> cellData.getValue().nameActivityroperty());
        dayColumn.setCellValueFactory(cellData -> cellData.getValue().dayProperty());
        oraInizioColumn.setCellValueFactory(cellData -> cellData.getValue().oraInizioProperty());
        oraFineColumn.setCellValueFactory(cellData -> cellData.getValue().oraFineProperty());
        
    }

    private void updateImage(user person) {
        if (person != null) {
            // Mostra l'ImageView e aggiorna l'immagine dell'atleta
            athleteImageView.setVisible(true); // Mostra l'immagine quando una persona è selezionata
    
            // Aggiungi logica per caricare una immagine diversa per ogni persona, se disponibile
            String imagePath = "/it/unife/lp/Image/" + person.getFirstName().toLowerCase() + "-icon.png"; // Ad esempio, nome-icon.png
            Image personImage = new Image(getClass().getResourceAsStream(imagePath));
            athleteImageView.setImage(personImage);
        } else {
            // Se non c'è una persona selezionata, nascondi l'immagine
            athleteImageView.setVisible(false); // Nascondi l'immagine
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        personTable.setItems(mainApp.getPersonData());
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showPersonDetails(newValue);
            if (newValue != null) {
                IscrittedPerson.setItems(newValue.getIscrizioni());
            } else {
                IscrittedPerson.setItems(null);
            }
        });
        
        //IscrittedPerson.setItems(mainApp.getIscr(personTable.getSelectionModel().getSelectedItem()));
    }

    
    private void showPersonDetails(user person) {
        if (person != null) {
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            emaiLabel.setText(person.getEmail());
            phoneNumberLabel.setText(Integer.toString(person.getPhoneNumber()));
            birthdayLabel.setText(DateUtil.format(person.getComple()));
            AttivitaPrefe.setText(person.toStringAttività());
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            phoneNumberLabel.setText("");
            emaiLabel.setText("");
            birthdayLabel.setText("");
            AttivitaPrefe.setText("");
        }
    }

    // Pulsante elimina
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
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

    //Pulsante New Atleta
    @FXML
    private void handleNewPerson() {
        user tempPerson = new user();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    //Pulsante Edit Atleta
    @FXML
    private void handleEditPerson() {
        user selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
        if (okClicked) {
            showPersonDetails(selectedPerson);
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

    // Pulsante Attività
    @FXML
    private void handleActivity() {
        mainApp.showActivities();
    }
}
