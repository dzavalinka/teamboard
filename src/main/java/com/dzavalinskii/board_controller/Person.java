package com.dzavalinskii.board_controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Person {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private Image avatar;
    private ArrayList<Integer> tags;
    public final int id;
    private static int counter;

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

    public Person(String name, String description) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.tags = new ArrayList<>();
        this.id = counter;
        counter++;
    }
}
