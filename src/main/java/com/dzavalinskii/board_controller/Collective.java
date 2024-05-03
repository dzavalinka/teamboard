package com.dzavalinskii.board_controller;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Collective {
    private SimpleStringProperty name;

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    private ArrayList<Tag> tags;
    private ArrayList<LinkType> linkTypes;
    private  ArrayList<Link> links;
    private ArrayList<Person> persons;
    private ArrayList<Board> boards;

}
