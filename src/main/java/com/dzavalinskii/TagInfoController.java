package com.dzavalinskii;

import com.dzavalinskii.util_classes.PersonInfo;
import com.dzavalinskii.util_classes.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TagInfoController implements Initializable {

    @FXML
    private Button cancel;

    @FXML
    private Button delete_tag;

    @FXML
    private Button ok;

    @FXML
    private ListView<PersonInfo> persons_with_tag;

    @FXML
    private TextField tag_name;

    @FXML
    void cancel(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void deleteTagBtnClicked(ActionEvent event) {
        DBUtils.deleteTag(tag.getId());
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void ok(ActionEvent event) {
        DBUtils.updateTag(tag_name.getText(), tag.getId());
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    private Tag tag;

    TagInfoController(Tag tag) {
        this.tag = tag;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tag_name.setText(tag.getName());
        persons_with_tag.setItems(DBUtils.loadPersonInfoByTag(tag.getId()));
    }
}
