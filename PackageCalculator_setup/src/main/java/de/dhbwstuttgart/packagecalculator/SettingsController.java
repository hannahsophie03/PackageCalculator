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

    @FXML
    public void initialize() {
        // CSV-Datei einlesen (überprüfe den Pfad zur Datei)
        serviceDataList = CsvReader.readCsv("/de/dhbwstuttgart/packagecalculator/config.csv");

        // Fülle die Dienstleister in die ComboBox, wenn die Datei erfolgreich geladen wurde
        if (!serviceDataList.isEmpty()) {
            for (Map<String, String> serviceData : serviceDataList) {
                serviceProviderComboBox.getItems().add(serviceData.get("Name"));
            }
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
        // Der Pfad zur CSV-Datei
        String filePath = "C:\\Users\\escherh\\DHBW\\PackageCalculator\\PackageCalculator_setup\\src\\main\\resources\\de\\dhbwstuttgart\\packagecalculator\\config.csv";
        CsvReader.writeCsv(filePath, serviceDataList);
        System.out.println("Einstellungen gespeichert für: " + selectedService);
    }

    @FXML
    protected void onCloseClicked() {
        // Schließe das Fenster
        Stage stage = (Stage) serviceProviderComboBox.getScene().getWindow();
        stage.close();
    }
}
