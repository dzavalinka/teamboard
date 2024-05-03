package com.dzavalinskii.board_controller;

import javafx.beans.property.SimpleIntegerProperty;

public class PersonLocation {
    private int x;
    private int y;
    public final int id;
    private static int counter;

    public PersonLocation(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = counter;
        counter++;
    }
}
