package com.dzavalinskii.util_classes;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class PersonListCell extends ListCell<Person> {
    private final Label name = new Label();
    private final Label description = new Label();
    private final VBox layout = new VBox(name, description);

    public PersonListCell() {
        super();
        name.setStyle("-fx-font-size: 20px;");
    }

    @Override
    protected void updateItem(Person item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);

        if(empty || item == null || item.getName() == null) {
            name.setText(null);
            description.setText(null);
            setGraphic(null);
        } else {
            name.setText(item.getName());
            description.setText(item.getDescription() != null ? item.getDescription() : "Описание отсутствует");
            setGraphic(layout);
        }
    }
}
