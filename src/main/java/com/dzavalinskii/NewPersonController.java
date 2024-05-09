package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewPersonController {

    @FXML
    private Button cancel_np;

    @FXML
    private TextField new_person_desc;

    @FXML
    private TextField new_person_name;

    @FXML
    private Button ok_np;

    @FXML
    void addNewPerson(ActionEvent event) {
        DBUtils.addPerson(new_person_name.getText(), new_person_desc.getText(), Main.currentCollectiveId);
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
