package com.dzavalinskii;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;


public class Main extends Application {

    public static long currentCollectiveId = 0;
    public static long currentBoardId = 0;

   /* public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DBUtils.jdbcURL, DBUtils.login, DBUtils.password);
        Statement s = connection.createStatement();
        ResultSet rs;
        //String s1 = "INSERT INTO personInfo (personId, boardId, x, y) VALUES (1, 1, 0, 0)";
        //String s2 = "INSERT INTO tags (name, collectiveId) VALUES ('cat', 1)";
        //String s3 = "INSERT INTO tags (name, collectiveId) VALUES ('dog', 1)";
        //s.executeUpdate(s1);
        //s.executeUpdate(s2);
        //s.executeUpdate(s3);
        String s1 = "SELECT * FROM tags WHERE REGEXP_LIKE(name, 'dog&cat', 'i')";
        rs = s.executeQuery(s1);
        rs.next();
        System.out.println(rs.getString(1));
        rs.next();
        System.out.println(rs.getString(1));
        // По очереди ищем вхождения, джойним, оставляем только &
        // INTERSECT
    }*/
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
