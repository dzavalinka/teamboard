package com.dzavalinskii.util_classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

public class LinkType {
    private LinkLineType lineType;
    private Color color;
    private SimpleStringProperty name;
    private boolean twoSided;
    public final int id;

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

    public LinkType(int id, LinkLineType lineType, Color color, String name, boolean twoSided) {
        this.lineType = lineType;
        this.color = color;
        this.name.set(name);
        this.twoSided = twoSided;
        this.id = id;
    }

    public static String colorToString(Color color) {
        String res = color.getRed() + " " + color.getBlue() + " " + color.getGreen() + " " + color.getOpacity();
        return  res;
    }

    public static Color stringToColor(String string) {
        String[] split = string.split(" ");
        Color res = new Color(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2]), Long.parseLong(split[3]));
        return res;
    }

    @Override
    public String toString() {
        return getName();
    }
}
