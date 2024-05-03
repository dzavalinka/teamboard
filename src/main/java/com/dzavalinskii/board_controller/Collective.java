package com.dzavalinskii.board_controller;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Collective {
    private SimpleStringProperty name;
    private ArrayList<Tag> tags;
    private ArrayList<LinkType> linkTypes;
    private ArrayList<Person> persons;
    private ArrayList<Board> boards;

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Collective(String name) {
        this.name = new SimpleStringProperty(name);
        tags = new ArrayList<>();
        linkTypes = new ArrayList<>();
        persons = new ArrayList<>();
        boards = new ArrayList<>();
    }
}
