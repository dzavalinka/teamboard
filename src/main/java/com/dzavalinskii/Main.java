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

    /*public static void main(String[] args) throws Exception {


        try (Connection connection = DriverManager.getConnection(DBUtils.jdbcURL)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("ALTER TABLE collectives ADD COLUMN description VARCHAR(255)");
            System.out.println("success");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("load_screen.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {

        }
    }
}
