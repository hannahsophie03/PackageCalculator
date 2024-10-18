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
                resultText.setText(String.format("Der beste Dienstleister ist %s mit Kosten von %.2f €", bestCarrier.getName(), lowestCost));
            } else {
                resultText.setText("Kein Versanddienstleister kann dieses Paket versenden.");
            }

        } catch (NumberFormatException e) {
            resultText.setText("Bitte geben Sie gültige Zahlenwerte ein.");
        } catch (IOException e) {
            resultText.setText("Fehler beim Laden der Konfigurationsdatei.");
        }
    }
}
