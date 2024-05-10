package com.dzavalinskii;

import com.dzavalinskii.util_classes.Board;
import com.dzavalinskii.util_classes.BoardListCell;
import com.dzavalinskii.util_classes.Person;
import com.dzavalinskii.util_classes.PersonListCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    private Button add_board_button;

    @FXML
    private Button add_person_button;

    @FXML
    private ScrollPane board_space;

    @FXML
    private ListView<Board> board_list;

    @FXML
    private ListView<Person> persons_list;

    @FXML
    public void newBoardBtnClick(ActionEvent event) throws  IOException {
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
    void  changePerson(MouseEvent event) throws IOException {
        Person person = persons_list.getSelectionModel().getSelectedItem();
        Parent root = null;
        if (person != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("change_person_data.fxml"));
            ChangePersonController controller = new ChangePersonController(person);
            loader.setController(controller);
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(person.getName());
            stage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Stage stageLoad = new Stage();
            Parent loadRoot = FXMLLoader.load(getClass().getResource("load_screen.fxml"));
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
            board_list.setItems(DBUtils.loadBoards());
            board_list.setCellFactory(param -> new BoardListCell());
            persons_list.setItems(DBUtils.loadPersons());
            persons_list.setCellFactory(param -> new PersonListCell());
            Node n = (Node) mouseEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.setTitle(DBUtils.loadCollectiveName(Main.currentCollectiveId));
        }
    }

    @FXML
    void search(ActionEvent event) {

    }
    @FXML
    void toLoadscreen(ActionEvent event) {
        try {
            Stage stageLoad = new Stage();
            Parent loadRoot = FXMLLoader.load(getClass().getResource("load_screen.fxml"));
            stageLoad.setScene(new Scene(loadRoot));
            stageLoad.setAlwaysOnTop(true);
            stageLoad.setTitle("Создайте новый коллектив или загрузите существующий");
            stageLoad.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newLinktypeBtnClick(ActionEvent event) {
        try {
            Stage stageLinktype = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("new_link_type.fxml"));
            stageLinktype.setScene(new Scene(root));
            stageLinktype.setTitle("Новый тип связи");
            stageLinktype.show();
            stageLinktype.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newLinkBtnClick(ActionEvent event) {
        try {
            Stage stageNewLink = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("new_link.fxml"));
            stageNewLink.setScene(new Scene(root));
            stageNewLink.setTitle("Новая связь");
            stageNewLink.show();
            stageNewLink.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newTagBtnClick(ActionEvent event) {
        try {
            Stage stageNewTag = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("new_tag.fxml"));
            stageNewTag.setScene(new Scene(root));
            stageNewTag.setTitle("Новый тег");
            stageNewTag.show();
            stageNewTag.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeBoard(MouseEvent mouseEvent) {

    }
}
