package com.dzavalinskii.board_controller;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Collective {
    private SimpleStringProperty name;
    private SimpleStringProperty description;

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Collective(String name) {
        this.name = new SimpleStringProperty(name);
    }
}
