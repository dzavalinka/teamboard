package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTagController {

    @FXML
    private Button cancel_nt;

    @FXML
    private Button ok_nt;

    @FXML
    private TextField tag_name;

    @FXML
    void cancel(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    public void addTag(ActionEvent actionEvent) {

    }
}
