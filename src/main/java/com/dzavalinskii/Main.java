package com.dzavalinskii;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class Main extends Application {

    public static int currentCollectiveId = 0;
    public static int currentBoardId = 0;

    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("main_screen.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
