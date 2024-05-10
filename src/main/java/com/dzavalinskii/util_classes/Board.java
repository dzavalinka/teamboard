package com.dzavalinskii.util_classes;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;

public class Board {
    public final int collectiveId;
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private Timestamp timestamp;

    public final int id;

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Board(int collectiveId, String name, String description, Timestamp timestamp, int id) {
        this.collectiveId = collectiveId;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.timestamp = timestamp;
        this.id = id;
    }
}
