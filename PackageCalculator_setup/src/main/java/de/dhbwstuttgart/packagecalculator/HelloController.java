package de.dhbwstuttgart.packagecalculator;

import config.Carrier;
import config.ConfigManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class HelloController {

    @FXML
    private TextField heightField;

    @FXML
    private TextField widthField;

    @FXML
    private TextField depthField;

    @FXML
    private TextField weightField;

    @FXML
    private Text resultText;

    @FXML
    private Text priceText;  // Neues Textfeld für den Preis

    @FXML
    protected void onCalculateClicked() {
        try {
            // Eingabewerte abrufen
            double height = Double.parseDouble(heightField.getText());
            double width = Double.parseDouble(widthField.getText());
            double depth = Double.parseDouble(depthField.getText());
            double weight = Double.parseDouble(weightField.getText());

            // Dienstleister laden
            ConfigManager configManager = new ConfigManager();
            List<Carrier> carriers = configManager.ladeDienstleister();

            // Günstigsten Dienstleister finden
            Carrier bestCarrier = null;
            double lowestCost = Double.MAX_VALUE;

            for (Carrier carrier : carriers) {
                if (carrier.canShip(height, width, depth, weight)) {
                    double cost = carrier.berechnePreis(weight);
                    if (cost < lowestCost) {
                        lowestCost = cost;
                        bestCarrier = carrier;
                    }
                }
            }

            // Ergebnis anzeigen
            if (bestCarrier != null) {
                // Setze den Preis in das Preis-Textfeld
                priceText.setText(String.format("Preis: %.2f €", lowestCost));

                // Setze den Dienstleister in das resultText-Textfeld
                resultText.setText("Angebot von: " + bestCarrier.getName());
            } else {
                resultText.setText("Kein Versanddienstleister kann dieses Paket versenden.");
                priceText.setText("");  // Leere das Preisfeld, wenn kein Dienstleister gefunden wurde
            }

        } catch (NumberFormatException e) {
            resultText.setText("Bitte geben Sie gültige Zahlenwerte ein.");
            priceText.setText("");  // Leere das Preisfeld bei Fehler
        } catch (IOException e) {
            resultText.setText("Fehler beim Laden der Konfigurationsdatei.");
            priceText.setText("");  // Leere das Preisfeld bei Fehler
        }
    }

    // Reaktion auf den "Einstellungen"-Button
    @FXML
    protected void onSettingsClicked() {
        // Logik für Einstellungen (z.B. ein neues Fenster öffnen oder Einstellungen anzeigen)
        System.out.println("Einstellungen-Button wurde geklickt");
    }
}
