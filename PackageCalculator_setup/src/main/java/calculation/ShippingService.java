package calculation;

import config.Carrier;
import config.ConfigManager;

import java.io.IOException;
import java.util.List;

public class ShippingService {

    /**
     * Führt die Berechnung der Versandkosten durch.
     *
     * @param height Höhe des Pakets in cm
     * @param width Breite des Pakets in cm
     * @param depth Tiefe des Pakets in cm
     * @param weight Gewicht des Pakets (in kg oder g)
     * @param isGramsSelected Gibt an, ob das Gewicht in Gramm angegeben ist
     * @return Das Ergebnis in Form eines Arrays von zwei Strings: [Preis, Dienstleistername]
     * @throws IOException Fehler beim Laden der Konfigurationsdatei
     */
    public String[] calculateShipping(double height, double width, double depth, double weight, boolean isGramsSelected, boolean isKilogramsSelected) throws IOException {
        // Wenn das Gewicht in Gramm angegeben ist, muss es in Kilogramm umgerechnet werden
        if (isGramsSelected) {
            System.out.println("Gewicht in g");
            weight = weight / 1000.0;  // Gramm in Kilogramm umrechnen
            System.out.println("Gewicht in kg: " + weight);
        }

        if (isKilogramsSelected) {
            System.out.println("Gewicht in kg");
        }

        // Gewicht darf nicht negativ oder 0 sein
        if (weight <= 0) {
            return new String[] { "Ungültiges Gewicht", "" };
        }

        // Dienstleister laden
        ConfigManager configManager = new ConfigManager();
        List<Carrier> carriers = configManager.loadCarrier();

        // Günstigsten Dienstleister finden
        Carrier bestCarrier = null;
        double lowestCost = Double.MAX_VALUE;

        for (Carrier carrier : carriers) {
            if (carrier.canShip(height, width, depth, weight)) {  // Maße in cm, Gewicht in kg
                double cost = carrier.calculatePrice(weight);
                if (cost < lowestCost) {
                    lowestCost = cost;
                    bestCarrier = carrier;
                }
            }
        }

        // Ergebnis zurückgeben
        if (bestCarrier != null) {
            return new String[] { String.format("%.2f €", lowestCost), bestCarrier.getName() };
        } else {
            return new String[] { "Kein Versanddienstleister kann dieses Paket versenden", "" };
        }
    }
}
