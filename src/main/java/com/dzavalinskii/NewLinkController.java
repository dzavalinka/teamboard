package com.dzavalinskii;

import com.dzavalinskii.util_classes.LinkType;
import com.dzavalinskii.util_classes.PersonInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewLinkController implements Initializable {

    @FXML
    private Button add_link_btn;

    @FXML
    private Button cancel;

    @FXML
    private ChoiceBox<LinkType> link_type_choicebox;

    @FXML
    private ChoiceBox<PersonInfo> p1_choicebox;

    @FXML
    private ChoiceBox<PersonInfo> p2_choicebox;

    @FXML
    void addLink(ActionEvent event) {
        try {
            DBUtils.addLink(Main.currentBoardId, link_type_choicebox.getValue().id,
                    p1_choicebox.getValue().getId(), p2_choicebox.getValue().getId());
            Node n = (Node) event.getSource();
            Stage currentStage = (Stage) n.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Выбраны не все параметры");
            alert.show();
        }
    }

    @FXML
    void cancel_nl(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        p1_choicebox.getItems().setAll(DBUtils.loadPersonInfosByBoard(Main.currentBoardId));
        p2_choicebox.getItems().setAll(DBUtils.loadPersonInfosByBoard(Main.currentBoardId));
        link_type_choicebox.getItems().setAll(DBUtils.loadLinkTypes());
    }
}
