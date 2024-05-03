package com.dzavalinskii;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("load_screen.fxml"));
            primaryStage.setScene(new Scene(root));
        } catch (Exception e) {

        }
    }
}
