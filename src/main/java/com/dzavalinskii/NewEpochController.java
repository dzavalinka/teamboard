package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewEpochController {

    @FXML
    private Button cancel_ne;

    @FXML
    private TextArea epoch_desc;

    @FXML
    private TextField epoch_name;

    @FXML
    private DatePicker epoch_timestamp;

    @FXML
    private Button ok_ne;

    @FXML
    void addNewEpoch(ActionEvent event) {

    }

    @FXML
    void cancel_ne(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }
}
