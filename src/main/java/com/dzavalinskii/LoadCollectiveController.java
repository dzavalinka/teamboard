package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LoadCollectiveController {

    @FXML
    private Button load_btn;

    @FXML
    private ListView<?> saved_list;

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
}
