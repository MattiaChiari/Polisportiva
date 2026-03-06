package it.unife.lp.view;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import it.unife.lp.MainApp;
import it.unife.lp.model.Attivita;
import it.unife.lp.model.user;

public class StatisticsContorller {
    
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    
    private ObservableList<String> activityNames = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Configura l'asse X con i nomi delle attività
        xAxis.setCategories(activityNames);
    }
    
    public void setStatistics(MainApp mainApp) {
        // Mappa per contare le iscrizioni per attività
        Map<String, Integer> activityCounts = new HashMap<>();

        // Inizializza il conteggio delle iscrizioni per ogni attività unica
        for (Attivita activity : mainApp.getActivityData()) {
            activityCounts.put(activity.getNameActivity(), 0);
        }

        // Conta le iscrizioni scorrendo gli utenti
        for (user u : mainApp.getPersonData()) {
            for (Attivita iscrizione : u.getIscrizioni()) {
                String activityName = iscrizione.getNameActivity();
                activityCounts.put(activityName, activityCounts.getOrDefault(activityName, 0) + 1);
            }
        }

        // Aggiungi i nomi delle attività all'asse X
        activityNames.clear();
        activityNames.addAll(activityCounts.keySet());

        // Popola il grafico a barre con i dati
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : activityCounts.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().clear();
        barChart.getData().add(series);
    }
}
