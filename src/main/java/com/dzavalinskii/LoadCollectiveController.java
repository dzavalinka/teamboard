package com.dzavalinskii;

import com.dzavalinskii.util_classes.Collective;
import com.dzavalinskii.util_classes.CollectiveListCell;
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

public class LoadCollectiveController implements Initializable {

    @FXML
    private Button load_btn;

    @FXML
    private ListView<Collective> saved_list;

    @FXML
    private TextField new_collective_name;

    @FXML
    private TextArea new_collective_desc;

    @FXML
    private Button create_collective_btn;

    @FXML
    private Button delete_btn;

    @FXML
    void createCollective(ActionEvent event) {
        Main.currentCollectiveId = DBUtils.addCollective(new_collective_name.getText(), new_collective_desc.getText());
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void deleteSelectedCollective(ActionEvent event) {
        DBUtils.deleteCollective(saved_list.getSelectionModel().getSelectedItem().getName());
        saved_list.setItems(DBUtils.loadCollectives());
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saved_list.setItems(DBUtils.loadCollectives());
        saved_list.setCellFactory(param -> new CollectiveListCell());
    }

    @FXML
    void loadCollective(ActionEvent event) {

    }
}
