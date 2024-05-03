package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class NewLinkTypeController {

    @FXML
    private Button cancel_nlt;

    @FXML
    private ColorPicker line_colour;

    @FXML
    private ToggleButton line_directed;

    @FXML
    private ChoiceBox<?> link_line_type;

    @FXML
    private TextField link_name;

    @FXML
    private Button ok_nlt;

    @FXML
    void addLinkType(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }

}
