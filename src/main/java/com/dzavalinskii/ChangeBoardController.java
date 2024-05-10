package com.dzavalinskii;

import com.dzavalinskii.util_classes.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ChangeBoardController implements Initializable {

    @FXML
    private TextArea board_desc_changed;

    @FXML
    private TextField board_name_changed;

    @FXML
    private DatePicker board_timestamp_changed;

    @FXML
    private Button cancel_eb;

    @FXML
    private Button delete_eb;

    @FXML
    private Button save_eb;

    @FXML
    void cancel(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void deleteBoardBtnClick(ActionEvent event) {
        DBUtils.deleteBoard(board.id);
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void saveBoardBtnClick(ActionEvent event) {
        Date date = new Date(board_timestamp_changed.getValue().toEpochDay());
        DBUtils.updateBoard(board_name_changed.getText(), board_desc_changed.getText(), date.getTime(), board.id);
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    private Board board;

    public ChangeBoardController(Board board) {
        this.board = board;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (board != null) {
            board_name_changed.setText(board.getName());
            board_desc_changed.setText(board.getDescription());
        }
    }
}
