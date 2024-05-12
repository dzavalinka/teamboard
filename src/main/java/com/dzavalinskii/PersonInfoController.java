package com.dzavalinskii;

import com.dzavalinskii.util_classes.PersonInfo;
import com.dzavalinskii.util_classes.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonInfoController implements Initializable {

    @FXML
    private ChoiceBox<Tag> add_tag_choicebox;

    @FXML
    private Button cancel;

    @FXML
    private Button delete_person_info;

    @FXML
    private ListView<Tag> persons_tags;

    @FXML
    private Button save_person_info;


    @FXML
    void cancel(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void deletePersonInfoBtnClicked(ActionEvent event) {
        DBUtils.deletePersonInfo(personInfo.getId());
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void savePersonInfoBtnClicked(ActionEvent event) {
        //TODO убрать одну из кнопок ок/отмена, сохранять теги в бд сразу.
    }

    private PersonInfo personInfo;
    public PersonInfoController (PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        persons_tags.setItems(DBUtils.loadPersonsTags(personInfo.getId()));
        add_tag_choicebox.getItems().addAll(DBUtils.loadPersonsTags(personInfo.getId()));
        add_tag_choicebox.setOnAction(this::addTag);
    }

    @FXML
    void removeTag() {
        //TODO
    }

    public void addTag(ActionEvent event) {
        long tagId = add_tag_choicebox.getValue().getId();
        DBUtils.addTagPerson(personInfo.getId(), tagId);
        persons_tags.setItems(DBUtils.loadPersonsTags(personInfo.getId()));
    }
}
