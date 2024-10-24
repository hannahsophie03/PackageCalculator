package calculation;

import config.Carrier;
import config.ConfigManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ShippingService {

    // Methode zur Berechnung der besten Versandoption
    public String[] calculateShipping(double height, double width, double depth, double weight, List<Map<String, String>> serviceDataList) {
        double gurtmass = height + 2 * width + 2 * depth;  // Berechnung des Gurtmaßes
        String[] result = {"Kein Versanddienstleister kann dieses Paket versenden", ""};
        double bestPrice = Double.MAX_VALUE;

        // Durchlaufe die Liste der Dienstleisterdaten
        for (Map<String, String> serviceData : serviceDataList) {
            double maxHeight = Double.parseDouble(serviceData.get("Height"));
            double maxWidth = Double.parseDouble(serviceData.get("Width"));
            double maxDepth = Double.parseDouble(serviceData.get("Depth"));
            double maxGurtmass = Double.parseDouble(serviceData.get("Gurtmass"));
            double maxWeight = Double.parseDouble(serviceData.get("Weight"));
            double price = Double.parseDouble(serviceData.get("Price"));

            // Überprüfe, ob die Paketmaße und das Gewicht innerhalb der Grenzen liegen
            if (height <= maxHeight && width <= maxWidth && depth <= maxDepth && gurtmass <= maxGurtmass && weight <= maxWeight) {
                if (price < bestPrice) {
                    bestPrice = price;
                    result[0] = String.format("%.2f €", bestPrice / 100);  // Preis in Euro umrechnen
                    result[1] = serviceData.get("Name");  // Dienstleistername
                }
            }
        }

        return result;
    }
}
