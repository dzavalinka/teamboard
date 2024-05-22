package com.dzavalinskii;

import com.dzavalinskii.util_classes.*;
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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
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
    private Pane board_space_pane;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("person_info.fxml"));
            PersonInfoController controller = new PersonInfoController(person);
            loader.setController(controller);
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(person.getName());
            stage.show();
        }
        event.consume();
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

    public void changeBoard(MouseEvent mouseEvent) throws IOException {
        Board board = board_list.getSelectionModel().getSelectedItem();
        Main.currentBoardId = board.id;
        fillBoard();
        if (mouseEvent.getClickCount() == 2) {
            Parent root = null;
            if (board != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("board_info.fxml"));
                BoardInfoController controller = new BoardInfoController(board);
                loader.setController(controller);
                root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle(board.getName());
                stage.show();
            }
        }
    }

    public void placePersonOnBoard(Person person, double x, double y) {
        x += board_space_pane.getScaleX()/2;
        y += board_space_pane.getScaleY()/2;
        DraggableMaker draggableMaker = new DraggableMaker();
        VBox vbox = new VBox();
        vbox.setPrefSize(35,45);
        vbox.setMaxSize(35,45);
        vbox.setMinSize(35,45);
        draggableMaker.makeDraggable(vbox);
        Circle circle = new Circle(18);
        Arc arc = new Arc();
        arc.setLength(180);
        arc.setRadiusX(29);
        arc.setRadiusY(49);
        PersonInfoLabel label = new PersonInfoLabel(person.getName(), 0);
        vbox.getChildren().add(circle);
        vbox.getChildren().add(arc);
        vbox.getChildren().add(label);
        long id = DBUtils.addPersonInfo(Main.currentBoardId, person.id);
        vbox.setOnMouseDragReleased(e-> updatePersonCoordinates(vbox.getLayoutX(), vbox.getLayoutY(), id));
        vbox.setLayoutX(x);
        vbox.setLayoutY(y);

        if (id != 0) {
            board_space_pane.getChildren().add(vbox);
        }
    }

    public void addPersonToBoard() {
        if (Main.currentBoardId != 0 && persons_list.getSelectionModel().getSelectedItem() != null) {
            Person person = persons_list.getSelectionModel().getSelectedItem();
            DBUtils.addPersonInfo(Main.currentBoardId, person.id);
            placePersonOnBoard(person, 0, 0);
        }
    }

    public void updatePersonCoordinates(double x, double y, long id) {
        DBUtils.updatePersonInfo(x, y, id);
    }

    public void fillBoard() {
        // Загружаем personInfo и links по Main.currentBoardId,
        board_space_pane.getChildren().clear();

    }

    public void collectiveInfoBtnClick(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("collective_info.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Информация о коллективе");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
