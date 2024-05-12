package com.dzavalinskii.util_classes;

public class Link {
    private long linkTypeId;
    private long person1;
    private long person2;
    private final long boardId;
    public final long id;

    public long getLinkTypeId() {
        return linkTypeId;
    }

    public void setLinkTypeId(long linkTypeId) {
        this.linkTypeId = linkTypeId;
    }

    public long getPerson1() {
        return person1;
    }

    public void setPerson1(long person1) {
        this.person1 = person1;
    }

    public long getPerson2() {
        return person2;
    }

    public void setPerson2(long person2) {
        this.person2 = person2;
    }

    public Link(long linkTypeId, long person1, long person2, long boardId, long id) {
        this.linkTypeId = linkTypeId;
        this.person1 = person1;
        this.person2 = person2;
        this.boardId = boardId;
        this.id = id;
    }
}
