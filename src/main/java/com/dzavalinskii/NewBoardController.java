package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewBoardController {

    @FXML
    private Button cancel_nb;

    @FXML
    private TextArea board_desc;

    @FXML
    private TextField board_name;

    @FXML
    private DatePicker board_timestamp;

    @FXML
    private Button ok_nb;

    @FXML
    void addNewBoard(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }
}
