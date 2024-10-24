package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private static final String CONFIG_FILE_PATH = "config.csv";  // Pfad relativ zu resources/

    public List<Carrier> loadCarrier() throws IOException {
        List<Carrier> carriers = new ArrayList<>();

        // Lade die CSV-Datei als Ressource
        InputStream inputStream = getClass().getResourceAsStream(CONFIG_FILE_PATH);

        if (inputStream == null) {
            throw new IOException("CSV-Datei nicht gefunden");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            br.readLine(); // Überspringe die Kopfzeile
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0];
                double maxHeight = Double.parseDouble(values[1]);
                double maxWidth = Double.parseDouble(values[2]);
                double maxDepth = Double.parseDouble(values[3]);
                double maxWeight = Double.parseDouble(values[4]);
                double pricePerKg = Double.parseDouble(values[5]);

                carriers.add(new Carrier(name, maxHeight, maxWidth, maxDepth, maxWeight, pricePerKg));
            }
        }
        return carriers;
    }
}
