package com.dzavalinskii.util_classes;

import java.util.ArrayList;

public class PersonInfo {
    private final long id;
    private long personId;
    private long boardId;
    private long x;
    private long y;

    public long getId() {
        return id;
    }
    public long getPersonId() {
        return personId;
    }

    public long getBoardId() {
        return boardId;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public PersonInfo(long id, long personId, long boardId, long x, long y) {
        this.id = id;
        this.personId = personId;
        this.boardId = boardId;
        this.x = x;
        this.y = y;
    }
}
