package com.dzavalinskii.board_controller;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Board implements Comparable<Board> {

    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private Date timestamp;
    public HashMap<Integer, PersonLocation> personLocations;
    private  ArrayList<Link> links;
    public final int id;
    private static int counter;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Board(String name, String description, Date timestamp) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.timestamp = timestamp;
        this.personLocations = new HashMap<>();
        this.links = new ArrayList<>();
        this.id = counter;
        counter++;
    }

    @Override
    public int compareTo(Board o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}
