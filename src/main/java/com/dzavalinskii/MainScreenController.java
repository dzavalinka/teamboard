package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    @FXML
    private Button add_epoch_button;

    @FXML
    private Button add_person_button;

    @FXML
    private ScrollPane board_space;

    @FXML
    private ListView<?> epoch_list;

    @FXML
    private ListView<?> persons_list;

    @FXML
    void newEpochBtnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("new_epoch.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
    }

    @FXML
    void newPersonBtnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("new_person.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
    }

    @FXML
    void  changePerson(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("change_person_data.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
    }
}
