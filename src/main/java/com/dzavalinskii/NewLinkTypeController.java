package com.dzavalinskii;

import com.dzavalinskii.util_classes.LinkLineType;
import com.dzavalinskii.util_classes.LinkType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class NewLinkTypeController implements Initializable {

    @FXML
    private Button cancel_nlt;

    @FXML
    private ColorPicker line_colour;

    @FXML
    private ToggleButton line_directed;

    @FXML
    private ChoiceBox<LinkLineType> link_line_type;

    @FXML
    private TextField link_name;

    @FXML
    private Button ok_nlt;

    @FXML
    void addLinkType(ActionEvent event) {
        if (link_name.getText().compareTo("") != 0) {
            DBUtils.addLinkType(LinkType.colorToString(line_colour.getValue()), link_name.getText(),
                    link_line_type.getValue().name(), line_directed.isSelected(), Main.currentCollectiveId);
            Node n = (Node) event.getSource();
            Stage currentStage = (Stage) n.getScene().getWindow();
            currentStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Введите название типа связи");
            alert.show();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void toggleDirected(ActionEvent event) {
        if (line_directed.getText().compareTo("Да") == 0) {
            line_directed.setText("Нет");
        } else {
            line_directed.setText("Да");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        link_line_type.getItems().setAll(LinkLineType.values());
        link_line_type.setValue(LinkLineType.SOLID);
    }
}
