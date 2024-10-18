package de.dhbwstuttgart.packagecalculator;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class SettingsController {

    @FXML
    private ComboBox<String> serviceProviderComboBox;

    @FXML
    private TextField maxHeightField;

    @FXML
    private TextField maxWidthField;

    @FXML
    private TextField maxDepthField;

    @FXML
    private TextField maxWeightField;

    @FXML
    private TextField pricePerKgField;

    private List<Map<String, String>> serviceDataList;

    // Pfad zur CSV-Datei
    private final String filePath = "C:\\Users\\escherh\\DHBW\\PackageCalculator\\PackageCalculator_setup\\src\\main\\resources\\de\\dhbwstuttgart\\packagecalculator\\config.csv";


    @FXML
    public void initialize() {
        // CSV-Datei einlesen
        loadCsvData();
    }

    // Methode zum Laden der CSV-Daten
    void loadCsvData() {
        // Debug-Ausgabe, um sicherzustellen, dass die Methode aufgerufen wird
        System.out.println("CSV-Daten werden geladen...");

        // CSV-Datei einlesen (überprüfe den Pfad zur Datei)
        // CSV-Datei einlesen (überprüfe den Pfad zur Datei)
        serviceDataList = CsvReader.readCsv("/de/dhbwstuttgart/packagecalculator/config.csv");

        // Fülle die Dienstleister in die ComboBox, wenn die Datei erfolgreich geladen wurde
        serviceProviderComboBox.getItems().clear();  // Entferne alte Einträge vor dem Neuladen
        if (!serviceDataList.isEmpty()) {
            for (Map<String, String> serviceData : serviceDataList) {
                serviceProviderComboBox.getItems().add(serviceData.get("Name"));
            }
            // Debug-Ausgabe, um die geladenen Daten zu überprüfen
            System.out.println("Daten erfolgreich geladen.");
        } else {
            System.err.println("Keine Daten verfügbar, möglicherweise wurde die Datei nicht geladen.");
        }
    }

    @FXML
    protected void onServiceProviderSelected() {
        // Ausgewählten Dienstleister anzeigen
        String selectedService = serviceProviderComboBox.getValue();

        for (Map<String, String> serviceData : serviceDataList) {
            if (serviceData.get("Name").equals(selectedService)) {
                maxHeightField.setText(serviceData.get("MaxHeight"));
                maxWidthField.setText(serviceData.get("MaxWidth"));
                maxDepthField.setText(serviceData.get("MaxDepth"));
                maxWeightField.setText(serviceData.get("MaxWeight"));
                pricePerKgField.setText(serviceData.get("PricePerKg"));
            }
        }
    }

    @FXML
    protected void onSaveClicked() {
        // Speichern der geänderten Werte
        String selectedService = serviceProviderComboBox.getValue();
        for (Map<String, String> serviceData : serviceDataList) {
            if (serviceData.get("Name").equals(selectedService)) {
                // Aktualisiere die Werte in der Liste
                serviceData.put("MaxHeight", maxHeightField.getText());
                serviceData.put("MaxWidth", maxWidthField.getText());
                serviceData.put("MaxDepth", maxDepthField.getText());
                serviceData.put("MaxWeight", maxWeightField.getText());
                serviceData.put("PricePerKg", pricePerKgField.getText());
            }
        }

        // Schreibe die aktualisierten Werte in die CSV-Datei
        CsvReader.writeCsv(filePath, serviceDataList);
        System.out.println("Einstellungen gespeichert für: " + selectedService);

        // Nach dem Speichern sofort die Daten neu laden, um sicherzustellen, dass die Änderungen sichtbar sind
        loadCsvData();
    }

    @FXML
    protected void onCloseClicked() {
        // Beim Schließen des Fensters wird die CSV-Datei neu geladen, um die aktuellsten Daten zu haben
        loadCsvData();

        // Schließe das Fenster
        Stage stage = (Stage) serviceProviderComboBox.getScene().getWindow();
        stage.close();
    }

}
