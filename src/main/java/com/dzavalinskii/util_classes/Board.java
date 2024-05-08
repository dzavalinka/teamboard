package com.dzavalinskii.util_classes;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Board implements Comparable<Board> {
    public final int collectiveId;
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private long timestamp;

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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Board(int collectiveId, String name, String description, long timestamp, int id) {
        this.collectiveId = collectiveId;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.timestamp = timestamp;
        this.id = id;
    }

    @Override
    public int compareTo(Board o) {
        return (int) (this.timestamp - o.timestamp);
    }
}
