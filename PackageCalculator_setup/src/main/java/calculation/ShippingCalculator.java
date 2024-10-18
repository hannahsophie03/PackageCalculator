package calculation;

import config.Carrier;

import java.util.List;

public class ShippingCalculator {

    /**
     * Berechnet den besten Versanddienstleister auf Basis der gegebenen Paketmaße und des Gewichts.
     *
     * @param height Höhe des Pakets
     * @param width Breite des Pakets
     * @param depth Tiefe des Pakets
     * @param weight Gewicht des Pakets
     * @param carriers Liste der verfügbaren Versanddienstleister
     * @return Der beste Carrier (Versanddienstleister), der das Paket zu den geringsten Kosten verschickt.
     */
    public Carrier calculateBestCarrier(double height, double width, double depth, double weight, List<Carrier> carriers) {
        Carrier bestCarrier = null;
        double lowestCost = Double.MAX_VALUE;

        for (Carrier carrier : carriers) {
            // Überprüfen, ob der Carrier das Paket verschicken kann
            if (carrier.canShip(height, width, depth, weight)) {
                // Berechnung des Preises
                double cost = carrier.berechnePreis(weight);
                // Den Carrier mit den niedrigsten Kosten auswählen
                if (cost < lowestCost) {
                    lowestCost = cost;
                    bestCarrier = carrier;
                }
            }
        }

        return bestCarrier;  // Gibt den besten Carrier zurück, oder null, falls keiner geeignet ist
    }
}
