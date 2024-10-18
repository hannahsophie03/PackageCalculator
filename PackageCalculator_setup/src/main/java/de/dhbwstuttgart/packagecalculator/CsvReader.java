package de.dhbwstuttgart.packagecalculator;

import java.io.*;
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



    public static void writeCsv(String filePath, List<Map<String, String>> serviceDataList) {
        // Hartcodierter Pfad zur Datei
        System.out.println("writeCsv wurde aufgerufen."); // Debugging-Ausgabe

        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Die Datei " + filePath + " existiert nicht.");
            return;
        }
        if (!file.canWrite()) {
            System.err.println("Die Datei " + filePath + " kann nicht beschrieben werden.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            System.out.println("Beginne mit dem Schreiben in die Datei: " + filePath);

            // Schreibe die Header-Zeile (Spaltennamen)
            writer.write("Name,MaxHeight,MaxWidth,MaxDepth,MaxWeight,PricePerKg");
            writer.newLine();

            // Schreibe die aktualisierten Dienstleister-Daten
            for (Map<String, String> serviceData : serviceDataList) {
                writer.write(serviceData.get("Name") + "," +
                        serviceData.get("MaxHeight") + "," +
                        serviceData.get("MaxWidth") + "," +
                        serviceData.get("MaxDepth") + "," +
                        serviceData.get("MaxWeight") + "," +
                        serviceData.get("PricePerKg"));
                writer.newLine();
            }

            System.out.println("Die Datei " + filePath + " wurde erfolgreich aktualisiert.");

        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der Datei: " + e.getMessage());
            e.printStackTrace();
        }
    }

}