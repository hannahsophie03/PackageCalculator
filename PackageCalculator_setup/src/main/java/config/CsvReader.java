package config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {
    public static List<Map<String, String>> readCsv(String filePath) {
        List<Map<String, String>> serviceDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Verwende das Semikolon als Trennzeichen
                String[] values = line.split(";");

                // Überprüfe, ob die Zeile die erwartete Anzahl an Werten enthält
                if (values.length == 7) {
                    Map<String, String> serviceData = new HashMap<>();
                    serviceData.put("Name", values[0]);
                    serviceData.put("Height", values[1]);
                    serviceData.put("Width", values[2]);
                    serviceData.put("Depth", values[3]);
                    serviceData.put("Girth", values[4]);
                    serviceData.put("Weight", values[5]);
                    serviceData.put("Price", values[6]);

                    serviceDataList.add(serviceData);

                    // Ausgabe in der Konsole zur Kontrolle
                    System.out.println("Gelesene Daten: Name=" + values[0] +
                            ", Height=" + values[1] +
                            ", Width=" + values[2] +
                            ", Depth=" + values[3] +
                            ", Girth=" + values[4] +
                            ", Weight=" + values[5] +
                            ", Price=" + values[6]);
                } else {
                    System.err.println("Ungültige Datenzeile: " + line);
                }
            }
        } catch (IOException e) {
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