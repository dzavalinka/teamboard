package com.dzavalinskii.util_classes;

import java.util.ArrayList;

public class PersonInfo {
    private final int id;
    private int personId;
    private int boardId;
    private int x;
    private int y;

    public int getId() {
        return id;
    }
    public int getPersonId() {
        return personId;
    }

    public int getBoardId() {
        return boardId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PersonInfo(int id, int personId, int boardId, int x, int y) {
        this.id = id;
        this.personId = personId;
        this.boardId = boardId;
        this.x = x;
        this.y = y;
    }
}
