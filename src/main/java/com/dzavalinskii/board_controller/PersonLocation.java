package com.dzavalinskii.board_controller;

public class PersonLocation {
    private int latitude;
    private int y;
    public final int id;
    private static int counter;

    public PersonLocation(int x, int y) {
        this.latitude = x;
        this.y = y;
        this.id = counter;
        counter++;
    }
}
