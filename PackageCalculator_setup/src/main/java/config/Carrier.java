package config;


public class Carrier {

    private String name;
    private double maxHeight, maxWidth, maxDepth, maxWeight, pricePerKg;

    public Carrier(String name, double maxHeight, double maxWidth, double maxDepth, double maxWeight, double pricePerKg) {
        this.name = name;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.maxDepth = maxDepth;
        this.maxWeight = maxWeight;
        this.pricePerKg = pricePerKg;
    }

    public String getName() {
        return name;
    }

    public boolean canShip(double height, double width, double depth, double weight) {
        return height <= maxHeight && width <= maxWidth && depth <= maxDepth && weight <= maxWeight;
    }

    public double berechnePreis(double weight) {
        return weight * pricePerKg;
    }
}
