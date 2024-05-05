package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class NewLinkController {

    @FXML
    private Button add_link_btn;

    @FXML
    private Button cancel;

    @FXML
    private ChoiceBox<?> link_type_choicebox;

    @FXML
    private ChoiceBox<?> p1_choicebox;

    @FXML
    private ChoiceBox<?> p2_choicebox;

    @FXML
    void addLink(ActionEvent event) {

    }

    @FXML
    void cancel_nl(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

}
