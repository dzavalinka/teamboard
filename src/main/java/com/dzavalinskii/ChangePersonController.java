package com.dzavalinskii;

import com.dzavalinskii.util_classes.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePersonController implements Initializable {

    @FXML
    private Button save_ep;

    @FXML
    private Button cancel;

    @FXML
    private TextArea person_desc_changed;

    @FXML
    private TextField person_name_changed;


    @FXML
    void cancel_ep(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void saveChanges(ActionEvent event) {
        DBUtils.updatePerson(person_name_changed.getText(), person_desc_changed.getText(), Main.currentCollectiveId);
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void deletePersonBtnAction(ActionEvent event) {
        DBUtils.deletePerson(person.id);
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    private Person person;
    public ChangePersonController(Person person) {
        this.person = person;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (person != null) {
            person_desc_changed.setText(person.getDescription());
            person_name_changed.setText(person.getName());
        }
    }
}
