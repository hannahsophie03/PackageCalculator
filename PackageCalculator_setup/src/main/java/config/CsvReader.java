package de.dhbwstuttgart.packagecalculator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    public static List<Map<String, String>> readCsv(String filePath) {
        List<Map<String, String>> serviceDataList = new ArrayList<>();
        String line;
        String[] headers = null;

        try (InputStream inputStream = CsvReader.class.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                // Datei wurde nicht gefunden
                System.err.println("Die Datei " + filePath + " wurde nicht gefunden.");
                return serviceDataList;
            }

            // Datei wurde gefunden, starte das Lesen
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Erste Zeile enthält die Header (Spaltennamen)
                if (lineNumber == 0) {
                    headers = values;
                } else {
                    // Map für jede Zeile, die einen Dienstleister repräsentiert
                    Map<String, String> serviceData = new HashMap<>();
                    for (int i = 0; i < values.length; i++) {
                        serviceData.put(headers[i], values[i]);
                    }
                    serviceDataList.add(serviceData);
                }
                lineNumber++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return serviceDataList;
    }
}
