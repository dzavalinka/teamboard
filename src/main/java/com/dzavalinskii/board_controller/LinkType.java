package com.dzavalinskii.board_controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

public class LinkType {
    private LinkLineType lineType;
    private Color color;
    private SimpleStringProperty name;
    private boolean twoSided;
    private final int id;
    private static int counter;

    public LinkLineType getLineType() {
        return lineType;
    }

    public void setLineType(LinkLineType lineType) {
        this.lineType = lineType;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LinkType(LinkLineType lineType, Color color, String name, boolean twoSided) {
        this.lineType = lineType;
        this.color = color;
        this.name.set(name);
        this.twoSided = twoSided;
        this.id = counter;
        counter++;
    }
}
