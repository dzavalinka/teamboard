package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;

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
        Date date = new Date(board_timestamp.getValue().toEpochDay());
        DBUtils.addBoard(Main.currentCollectiveId, board_name.getText(), board_desc.getText(), date.getTime());
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void cancel(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }
}
