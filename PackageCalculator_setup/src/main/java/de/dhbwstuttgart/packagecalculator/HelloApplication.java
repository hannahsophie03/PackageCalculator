package de.dhbwstuttgart.packagecalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        primaryStage.setTitle("Versandkostenrechner");

        Scene scene = new Scene(root);

        // Setze den Vollbildmodus
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");  // Entfernt den Hinweis, wie der Vollbildmodus verlassen wird
        primaryStage.setFullScreenExitKeyCombination(null);  // Deaktiviert die ESC-Taste für den Ausstieg

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}