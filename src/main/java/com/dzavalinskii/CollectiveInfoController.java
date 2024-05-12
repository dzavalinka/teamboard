package com.dzavalinskii;

import com.dzavalinskii.util_classes.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CollectiveInfoController implements Initializable {

    @FXML
    private ListView<Board> boards_in_collective;

    @FXML
    private Button cancel;

    @FXML
    private TextArea collective_description;

    @FXML
    private TextField collective_name;

    @FXML
    private Button delete;

    @FXML
    private ListView<LinkType> linktypes_in_collective;

    @FXML
    private Button ok;

    @FXML
    private ListView<Person> persons_in_collective;

    @FXML
    private ListView<Tag> tags_in_collective;

    @FXML
    void cancel(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void delete(ActionEvent event) {
        DBUtils.deleteCollective(Main.currentCollectiveId);
        Main.currentCollectiveId = 0;
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void save_changes(ActionEvent event) {
        DBUtils.updateCollective(Main.currentCollectiveId, collective_name.getText(), collective_description.getText());
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Collective collective = DBUtils.loadCollective(Main.currentCollectiveId);
        collective_name.setText(collective.getName());
        collective_description.setText(collective.getDescription());
        boards_in_collective.setItems(DBUtils.loadBoards());
        boards_in_collective.setCellFactory(param -> new BoardListCell());
        persons_in_collective.setItems(DBUtils.loadPersons());
        persons_in_collective.setCellFactory(param -> new PersonListCell());
        linktypes_in_collective.setItems(DBUtils.loadLinkTypes());
        tags_in_collective.setItems(FXCollections.observableArrayList(DBUtils.loadTags()));
    }
}
