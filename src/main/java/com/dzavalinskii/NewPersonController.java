package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

public class NewPersonController {

    @FXML
    private Button cancel_np;

    @FXML
    private TextField new_person_desc;

    @FXML
    private TextField new_person_name;

    @FXML
    private CheckComboBox<?> new_person_taglist;

    @FXML
    private Button ok_np;

    @FXML
    void addNewPerson(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }

}
