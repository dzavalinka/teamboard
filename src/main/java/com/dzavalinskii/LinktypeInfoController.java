package com.dzavalinskii;

import com.dzavalinskii.util_classes.Link;
import com.dzavalinskii.util_classes.LinkLineType;
import com.dzavalinskii.util_classes.LinkType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LinktypeInfoController implements Initializable {

    @FXML
    private Button cancel;

    @FXML
    private ColorPicker color_linktype;

    @FXML
    private Button delete;

    @FXML
    private ChoiceBox<LinkLineType> linetype_linktype;

    @FXML
    private ListView<Link> links;

    @FXML
    private TextField name_linktype;

    @FXML
    private Button ok;

    @FXML
    void cancel(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void deleteLinktype(ActionEvent event) {
        DBUtils.deleteLinkType(linkType.id);
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void saveChanges(ActionEvent event) {
        DBUtils.updateLinkType(LinkType.colorToString(color_linktype.getValue()), name_linktype.getText(), linetype_linktype.getValue().name(), linkType.id);
        Node n = (Node) event.getSource();
        Stage currentStage = (Stage) n.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void deleteLink(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Подтвердите удаление связи");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DBUtils.deleteLink(links.getSelectionModel().getSelectedItem().id);
            links.setItems(DBUtils.loadLinksByLinktype(linkType.id));
        }
    }

    private LinkType linkType;
    public LinktypeInfoController(LinkType linkType) {
        this.linkType = linkType;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name_linktype.setText(linkType.getName());
        color_linktype.setValue(linkType.getColor());
        linetype_linktype.getItems().setAll(LinkLineType.values());
        linetype_linktype.setValue(linkType.getLineType());
        links.setItems(DBUtils.loadLinksByLinktype(linkType.id));
    }
}
