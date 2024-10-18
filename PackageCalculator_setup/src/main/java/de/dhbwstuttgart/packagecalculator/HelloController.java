package de.dhbwstuttgart.packagecalculator;

import calculation.ShippingService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javafx.scene.*;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import java.io.IOException;



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
    private CheckBox gramsCheckBox;

    @FXML
    private CheckBox kilogramsCheckBox;

    @FXML
    private Text resultText;

    @FXML
    private Text priceText;


        // Methode wird aufgerufen, wenn auf den Button "Berechnen" geklickt wird
    @FXML
    protected void onCalculateClicked() {
        try {
            // Eingabewerte für Höhe, Breite und Tiefe abrufen (Maße in cm)
            double height = Double.parseDouble(heightField.getText());
            double width = Double.parseDouble(widthField.getText());
            double depth = Double.parseDouble(depthField.getText());
            double weight = Double.parseDouble(weightField.getText());  // Eingegebenes Gewicht




            // Überprüfen, ob eine Gewichtseinheit (g oder kg) ausgewählt wurde
            boolean isGramsSelected = gramsCheckBox.isSelected();
            boolean isKilogramsSelected = kilogramsCheckBox.isSelected();

            // Überprüfung und Print Statements
            if (isGramsSelected) {
                System.out.println("Gramm wurde ausgewählt.");
            }
            if (isKilogramsSelected) {
                System.out.println("Kilogramm wurde ausgewählt.");
            }




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
