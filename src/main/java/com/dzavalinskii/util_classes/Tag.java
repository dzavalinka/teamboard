package com.dzavalinskii.util_classes;

public class Tag {
    private final int id;
    private String name;
    private int collectiveId;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCollectiveId() {
        return collectiveId;
    }

    public Tag(int id, String name, int collectiveId) {
        this.name = name;
        this.id = id;
        this.collectiveId = collectiveId;
    }

    @Override
    public String toString() {
        return name;
    }
}
