package com.dzavalinskii.util_classes;

import javafx.beans.property.SimpleStringProperty;


public class Collective {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    public final int id;

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

    public Collective(int id, String name, String description) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.id = id;
    }
}
