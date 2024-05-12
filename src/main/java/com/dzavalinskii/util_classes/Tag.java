package com.dzavalinskii.util_classes;

public class Tag {
    private final long id;
    private String name;
    private long collectiveId;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCollectiveId() {
        return collectiveId;
    }

    public Tag(long id, String name, long collectiveId) {
        this.name = name;
        this.id = id;
        this.collectiveId = collectiveId;
    }

    @Override
    public String toString() {
        return name;
    }
}
