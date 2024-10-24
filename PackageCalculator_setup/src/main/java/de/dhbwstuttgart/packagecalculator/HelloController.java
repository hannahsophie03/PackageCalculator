package de.dhbwstuttgart.packagecalculator;

import calculation.ShippingService;
import config.CsvReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HelloController {

    public Rectangle headerBar;
    public ScrollPane logScrollPane;
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

    @FXML
    private TextFlow logTextFlow;  // TextFlow für die Logs

    @FXML
    protected void onCalculateClicked() {
        try {
            appendLog("Berechnung gestartet...\n", "text-log-info");

            // Eingabewerte für Höhe, Breite und Tiefe abrufen (sowohl für das Rechteck als auch für die Paketberechnung)
            double height = Double.parseDouble(heightField.getText());
            double width = Double.parseDouble(widthField.getText());
            double depth = Double.parseDouble(depthField.getText());
            double weight = Double.parseDouble(weightField.getText());  // Eingegebenes Gewicht

            appendLog("Eingabewerte - Höhe: " + height + ", Breite: " + width + ", Tiefe: " + depth + ", Gewicht: " + weight + "\n", "text-log-info");

            // Überprüfen, ob eine Gewichtseinheit (g oder kg) ausgewählt wurde
            boolean isGramsSelected = gramsCheckBox.isSelected();
            boolean isKilogramsSelected = kilogramsCheckBox.isSelected();

            if (!isGramsSelected && !isKilogramsSelected) {
                resultText.setText("Bitte wählen Sie eine Gewichtseinheit (g oder kg) aus.");
                appendLog("Fehler: Keine Gewichtseinheit ausgewählt.\n", "text-log-error");  // Log in rot
                return;
            }

            if (isGramsSelected && isKilogramsSelected) {
                resultText.setText("Bitte wählen Sie entweder g oder kg, nicht beide.");
                appendLog("Fehler: Beide Gewichtseinheiten wurden ausgewählt.\n", "text-log-error");  // Log in rot
                return;
            }

            appendLog("Gewichtseinheit ausgewählt: " + (isGramsSelected ? "Gramm" : "Kilogramm") + "\n", "text-log-info");

            // Versandlogik an ShippingService übergeben
            ShippingService shippingService = new ShippingService();
            List<Map<String, String>> serviceDataList = CsvReader.readCsv("/de/dhbwstuttgart/packagecalculator/config.csv"); // Die CSV-Datei laden
            String[] result = shippingService.calculateShipping(height, width, depth, weight, serviceDataList);

            // Versand-Ergebnis anzeigen
            if (!result[0].equals("Ungültiges Gewicht") && !result[0].equals("Kein Versanddienstleister kann dieses Paket versenden")) {
                priceText.setText("Preis: " + result[0]);
                resultText.setText("Angebot von: " + result[1]);
                appendLog("Berechnung abgeschlossen: Preis: " + result[0] + ", Dienstleister: " + result[1] + "\n", "text-log-info");
            } else {
                resultText.setText(result[0]);
                priceText.setText("");  // Preisfeld leeren, wenn kein Dienstleister gefunden wurde
                appendLog("Fehler: " + result[0] + "\n", "text-log-error");  // Log in rot
            }

            // Skalierungsfaktor, um die Box größer darzustellen
            double scaleFactor = 10.0;  // Erhöhe diesen Wert, um die Box größer zu machen

            // Dynamische Anpassung des Rechtecks basierend auf den Eingabewerten für Breite und Höhe
            if (width > 0 && height > 0) {
                rectangle.setWidth(width * scaleFactor);
                rectangle.setHeight(height * scaleFactor);

                // Labels mit den eingegebenen Paketmaßen aktualisieren
                widthLabel.setText("Breite: " + width + " cm");
                heightLabel.setText("Höhe: " + height + " cm");
                depthLabel.setText("Tiefe: " + depth + " cm");

                // Positionierung der Labels an die entsprechenden Kanten des Rechtecks
                adjustLabelPositions();

            } else {
                resultText.setText("Bitte geben Sie positive Werte für Breite und Höhe ein.");
            }

        } catch (NumberFormatException e) {
            resultText.setText("Bitte geben Sie gültige Zahlenwerte ein.");
            priceText.setText("");  // Preisfeld leeren bei Fehler
            appendLog("Fehler: Ungültige Zahlenwerte eingegeben.\n", "text-log-error");  // Log in rot
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

    @FXML
    protected void onResetClicked() {
        // Leert alle Eingabefelder
        heightField.clear();
        widthField.clear();
        depthField.clear();
        weightField.clear();

        // Setze das Rechteck auf die Standardmaße zurück (z.B. 100x100)
        rectangle.setWidth(371);  // Setze die Breite des Rechtecks auf einen Standardwert
        rectangle.setHeight(213);  // Setze die Höhe des Rechtecks auf einen Standardwert

        // Aktualisiere die Labeltexte für die Standardmaße
        widthLabel.setText("Breite: ... cm");
        heightLabel.setText("Höhe: ... cm");
        depthLabel.setText("Tiefe: ... cm");

        // Setze die Position der Labels neu
        adjustLabelPositions();

        // Log hinzufügen
        appendLog("Eingabefelder und Rechteck auf Standardmaße zurückgesetzt.\n", "text-log-info");
    }



    @FXML
    protected void onSettingsClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
            Parent root = loader.load();

            // Erstelle ein neues Einstellungsfenster
            Stage settingsStage = new Stage();
            settingsStage.setTitle("Einstellungen");

            Scene settingsScene = new Scene(root);
            settingsStage.setScene(settingsScene);

            // Setze den Vollbildmodus für das Einstellungsfenster
            settingsStage.setFullScreen(true);
            settingsStage.setFullScreenExitHint("");
            settingsStage.setFullScreenExitKeyCombination(null);


            // Zeige das Einstellungsfenster
            settingsStage.show();

            // Stelle sicher, dass die Startseite nach dem Schließen der Einstellungen wieder im Vollbildmodus bleibt
            settingsStage.setOnHidden(event -> {
                Stage primaryStage = (Stage) heightField.getScene().getWindow();
                primaryStage.setFullScreen(true);
            });

            appendLog("Einstellungsfenster geöffnet.\n", "text-log-info");

        } catch (IOException e) {
            e.printStackTrace();
            appendLog("Fehler: Einstellungsfenster konnte nicht geöffnet werden.\n", "text-log-error");
        }
    }

    // Methode zum Hinzufügen von Logs
    private void appendLog(String message, String styleClass) {
        Text logText = new Text(message);
        logText.getStyleClass().addAll("text-log", styleClass);  // Wendet die Styles an
        logTextFlow.getChildren().add(logText);

        // Automatisches Scrollen nach unten
        logScrollPane.setVvalue(1.0);
    }



    // Verknüpfe die AnchorPane mit fx:id
    @FXML
    private AnchorPane fx3DBox;

    // Methode zum Setzen der Höhe und Breite
    public void set3DBoxSize(double width, double height) {
        fx3DBox.setPrefWidth(width);
        fx3DBox.setPrefHeight(height);
        appendLog("Box-Größe gesetzt auf Breite: " + width + ", Höhe: " + height + "\n", "text-log-info");
    }

    @FXML
    protected void onCloseClicked() {
        appendLog("Anwendung wird geschlossen...\n", "text-log-info");
        // Schließt die Anwendung
        Platform.exit();
        System.exit(0);  // Optional: Beendet die JVM vollständig
    }
}
