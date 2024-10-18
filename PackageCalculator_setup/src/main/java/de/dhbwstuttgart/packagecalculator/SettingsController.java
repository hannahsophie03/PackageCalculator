package de.dhbwstuttgart.packagecalculator;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SettingsController {

    // Beispielmethode, die beim Speichern von Einstellungen aufgerufen wird
    @FXML
    protected void onSaveClicked() {
        System.out.println("Einstellungen wurden gespeichert.");
    }

    @FXML
    protected void onCloseClicked(javafx.event.ActionEvent event) {
        // Schließe das Einstellungsfenster
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
