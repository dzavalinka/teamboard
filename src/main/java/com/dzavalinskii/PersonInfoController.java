package com.dzavalinskii;

import com.dzavalinskii.util_classes.PersonInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class PersonInfoController {

    @FXML
    private Button add_tag_btn;

    @FXML
    private Button cancel;

    @FXML
    private Button delete_person_info;

    @FXML
    private ListView<?> persons_tags;

    @FXML
    private Button save_person_info;

    @FXML
    void addTagBtnClicked(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void deletePersonInfoBtnClicked(ActionEvent event) {

    }

    @FXML
    void savePersonInfoBtnClicked(ActionEvent event) {

    }

    private PersonInfo personInfo;
    public PersonInfoController (PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

}
