package com.dzavalinskii.board_controller;

import javafx.beans.property.SimpleStringProperty;

/*
 * Класс, представляющий тег - некое свойство персоны.
 * У тега есть имя и числовой идентификатор.
 */
public class Tag {
    private SimpleStringProperty name;
    public final int id;
    private static int counter;

    public String getName(){ return name.get();}
    public void setName(String value){ name.set(value);}

    Tag(String name) throws Exception {
        if (name.contains("&")||name.contains("|")||name.contains("!")) {
        this.id = counter;
        counter++;
        this.name = new SimpleStringProperty(name);
        } else {
            throw new Exception("Некорректное название тега");
        }
    }
}
