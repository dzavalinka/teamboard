package com.dzavalinskii;

import com.dzavalinskii.util_classes.Board;
import com.dzavalinskii.util_classes.PersonInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class BoardInfoController implements Initializable {

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
    private ListView<PersonInfo> person_infos;

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

    public BoardInfoController(Board board) {
        this.board = board;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (board != null) {
            board_name_changed.setText(board.getName());
            board_desc_changed.setText(board.getDescription());
        }
    }

    public void toPersonOnBoardInfo(MouseEvent mouseEvent) throws IOException {
        Node n = (Node) mouseEvent.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("link_type_info.fxml"));
        PersonOnBoardInfoController controller = new PersonOnBoardInfoController(person_infos.getSelectionModel().getSelectedItem());
        loader.setController(controller);
        Parent root = loader.load();
        currentStage.setTitle("");
        currentStage.setScene(new Scene(root));
    }
}
