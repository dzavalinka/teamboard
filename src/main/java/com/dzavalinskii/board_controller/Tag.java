package com.dzavalinskii.board_controller;

public class Tag {
    private final int id;
    private static int counter;
    private String name;

    public Tag(String name) {
        this.name = name;
        this.id = counter;
        counter++;
    }
}
