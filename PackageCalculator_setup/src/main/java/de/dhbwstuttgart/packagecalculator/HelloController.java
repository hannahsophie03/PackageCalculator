package de.dhbwstuttgart.packagecalculator;

import calculation.ShippingService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

public class HelloController {

    @FXML
    private TextField heightField;  // Dieses Feld wird sowohl für das Rechteck als auch für die Paketberechnung verwendet

    @FXML
    private TextField widthField;   // Dieses Feld wird ebenfalls wiederverwendet

    @FXML
    private TextField depthField;   // Für die Paketberechnung

    @FXML
    private TextField weightField;  // Für die Paketberechnung

    @FXML
    private CheckBox gramsCheckBox;

    @FXML
    private CheckBox kilogramsCheckBox;

    @FXML
    private Text resultText;

    @FXML
    private Text priceText;

    @FXML
    private Rectangle rectangle;  // Rechteck, dessen Größe dynamisch angepasst wird

    // Methode, die beim Klick auf den "Berechnen"-Button aufgerufen wird
    @FXML
    protected void onCalculateClicked() {
        try {
            // Eingabewerte für Höhe und Breite abrufen (sowohl für das Rechteck als auch für die Paketberechnung)
            double height = Double.parseDouble(heightField.getText());
            double width = Double.parseDouble(widthField.getText());
            double depth = Double.parseDouble(depthField.getText());
            double weight = Double.parseDouble(weightField.getText());  // Eingegebenes Gewicht

            // Überprüfen, ob eine Gewichtseinheit (g oder kg) ausgewählt wurde
            boolean isGramsSelected = gramsCheckBox.isSelected();
            boolean isKilogramsSelected = kilogramsCheckBox.isSelected();

            // Überprüfung und Print Statements
            if (!isGramsSelected && !isKilogramsSelected) {
                resultText.setText("Bitte wählen Sie eine Gewichtseinheit (g oder kg) aus.");
                return;
            }

            if (isGramsSelected && isKilogramsSelected) {
                resultText.setText("Bitte wählen Sie entweder g oder kg, nicht beide.");
                return;
            }

            // Logik in den ShippingService auslagern
            ShippingService shippingService = new ShippingService();
            String[] result = shippingService.calculateShipping(height, width, depth, weight, isGramsSelected, isKilogramsSelected);

            // Ergebnis anzeigen
            if (!result[0].equals("Ungültiges Gewicht") && !result[0].equals("Kein Versanddienstleister kann dieses Paket versenden")) {
                priceText.setText("Preis: " + result[0]);
                resultText.setText("Angebot von: " + result[1]);
            } else {
                resultText.setText(result[0]);
                priceText.setText("");  // Preisfeld leeren, wenn kein Dienstleister gefunden wurde
            }

            // Dynamische Anpassung des Rechtecks basierend auf den Eingabewerten für Breite und Höhe
            if (width > 0 && height > 0) {
                rectangle.setWidth(width);
                rectangle.setHeight(height);
                resultText.setText("Das Rechteck wurde auf die neuen Maße angepasst.");
            } else {
                resultText.setText("Bitte geben Sie positive Werte für Breite und Höhe ein.");
            }

        } catch (NumberFormatException e) {
            resultText.setText("Bitte geben Sie gültige Zahlenwerte ein.");
            priceText.setText("");  // Preisfeld leeren bei Fehler
        } catch (IOException e) {
            resultText.setText("Fehler beim Laden der Konfigurationsdatei.");
            priceText.setText("");  // Preisfeld leeren bei Fehler
        }
    }

    // Reaktion auf den "Einstellungen"-Button
    @FXML
    protected void onSettingsClicked() {
        System.out.println("Einstellungen-Button wurde geklickt");
    }
}
