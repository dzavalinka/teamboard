package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangePersonController {

    @FXML
    private Button add_tag_btn;

    @FXML
    private Button cancel;

    @FXML
    private TextArea person_desc_changed;

    @FXML
    private TextField person_name_changed;

    @FXML
    private ListView<?> person_taglist_changed;

    @FXML
    void cancel_ep(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    public void saveChanges(ActionEvent actionEvent) {

    }
}
