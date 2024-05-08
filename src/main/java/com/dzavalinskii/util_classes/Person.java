package com.dzavalinskii.util_classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class Person {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private Image avatar;
    public int id;
    private int collectiveId;

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

    public Person(int id, String name, String description, int collectiveId) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.id = id;
        this.collectiveId = collectiveId;

    }
}
