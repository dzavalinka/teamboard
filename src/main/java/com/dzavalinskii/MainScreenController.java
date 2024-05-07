package com.dzavalinskii;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    private Button add_epoch_button;

    @FXML
    private Button add_person_button;

    @FXML
    private ScrollPane board_space;

    @FXML
    private ListView<?> epoch_list;

    @FXML
    private ListView<?> persons_list;

    @FXML
    void newEpochBtnClick(ActionEvent event) throws IOException {
        if (Main.currentCollectiveId != 0) {
            Parent root = FXMLLoader.load(getClass().getResource("new_board.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Коллектив не выбран");
            alert.show();
        }
    }

    @FXML
    void newPersonBtnClick(ActionEvent event) throws IOException {
        if (Main.currentCollectiveId != 0) {
            Parent root = FXMLLoader.load(getClass().getResource("new_person.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Коллектив не выбран");
            alert.show();
        }
    }

    @FXML
    void  changePerson(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("change_person_data.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Stage stageLoad = new Stage();
            Parent loadRoot = null;
            loadRoot = FXMLLoader.load(getClass().getResource("load_screen.fxml"));
            stageLoad.setScene(new Scene(loadRoot));
            stageLoad.setAlwaysOnTop(true);
            stageLoad.setTitle("Создайте новый коллектив или загрузите существующий");
            stageLoad.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCollective(MouseEvent mouseEvent) {
        if (Main.currentCollectiveId == 0) {
            return;
        } else {

        }
    }

    private void loadPersons() {

    }

    private void loadBoards() {

    }
}
