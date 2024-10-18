package de.dhbwstuttgart.packagecalculator;

import calculation.ShippingService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {

    @FXML
    private TextField heightField;  // Höhe des Pakets

    @FXML
    private TextField widthField;   // Breite des Pakets

    @FXML
    private TextField depthField;   // Tiefe des Pakets

    @FXML
    private TextField weightField;  // Gewicht des Pakets

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

    @FXML
    private Label widthLabel;     // Label zur Anzeige der aktuellen Breite des Pakets
    @FXML
    private Label heightLabel;    // Label zur Anzeige der aktuellen Höhe des Pakets
    @FXML
    private Label depthLabel;     // Label zur Anzeige der aktuellen Tiefe des Pakets

    // Methode, die beim Klick auf den "Berechnen"-Button aufgerufen wird
    @FXML
    protected void onCalculateClicked() {
        try {
            // Eingabewerte für Höhe, Breite und Tiefe abrufen (sowohl für das Rechteck als auch für die Paketberechnung)
            double height = Double.parseDouble(heightField.getText());
            double width = Double.parseDouble(widthField.getText());
            double depth = Double.parseDouble(depthField.getText());
            double weight = Double.parseDouble(weightField.getText());  // Eingegebenes Gewicht

            // Überprüfen, ob eine Gewichtseinheit (g oder kg) ausgewählt wurde
            boolean isGramsSelected = gramsCheckBox.isSelected();
            boolean isKilogramsSelected = kilogramsCheckBox.isSelected();

            if (!isGramsSelected && !isKilogramsSelected) {
                resultText.setText("Bitte wählen Sie eine Gewichtseinheit (g oder kg) aus.");
                return;
            }

            if (isGramsSelected && isKilogramsSelected) {
                resultText.setText("Bitte wählen Sie entweder g oder kg, nicht beide.");
                return;
            }

            // Versandlogik an ShippingService übergeben
            ShippingService shippingService = new ShippingService();
            String[] result = shippingService.calculateShipping(height, width, depth, weight, isGramsSelected, isKilogramsSelected);

            // Versand-Ergebnis anzeigen
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

                // Labels mit den eingegebenen Paketmaßen aktualisieren
                widthLabel.setText("Breite: " + width + " cm");
                heightLabel.setText("Höhe: " + height + " cm");
                depthLabel.setText("Tiefe: " + depth + " cm");

                // Positionierung der Labels an die entsprechenden Kanten des Rechtecks
                adjustLabelPositions();

                resultText.setText("Das Rechteck und die Paketmaße wurden angepasst.");
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

    // Methode zur dynamischen Anpassung der Label-Positionen an den Kanten des Rechtecks
    private void adjustLabelPositions() {
        // Positioniere das Breiten-Label über dem Rechteck
        widthLabel.setLayoutX(rectangle.getLayoutX() + rectangle.getWidth() / 2 - widthLabel.getWidth() / 2);
        widthLabel.setLayoutY(rectangle.getLayoutY() - 20);  // Oberhalb des Rechtecks

        // Positioniere das Höhen-Label rechts neben dem Rechteck
        heightLabel.setLayoutX(rectangle.getLayoutX() + rectangle.getWidth() + 10);  // Rechts vom Rechteck
        heightLabel.setLayoutY(rectangle.getLayoutY() + rectangle.getHeight() / 2 - heightLabel.getHeight() / 2);

        // Positioniere das Tiefen-Label unter dem Rechteck
        depthLabel.setLayoutX(rectangle.getLayoutX() + rectangle.getWidth() / 2 - depthLabel.getWidth() / 2);
        depthLabel.setLayoutY(rectangle.getLayoutY() + rectangle.getHeight() + 10);  // Unterhalb des Rechtecks
    }

    // Reaktion auf den "Einstellungen"-Button
    @FXML
    protected void onSettingsClicked() {
        System.out.println("Einstellungen-Button wurde geklickt");
    }
}
