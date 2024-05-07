package com.dzavalinskii;

import com.dzavalinskii.board_controller.Collective;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
        DBUtils.addCollective(new_collective_name.getText(), new_collective_desc.getText());
        // Переход на экран редактора доделать
    }

    @FXML
    void deleteSelectedCollective(ActionEvent event) {

    }

    @FXML
    void loadCollective(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saved_list = new ListView<>(DBUtils.loadCollectives());
        saved_list.setCellFactory(param -> new CollectiveListCell());
    }
}
