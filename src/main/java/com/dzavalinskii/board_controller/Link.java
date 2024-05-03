package com.dzavalinskii.board_controller;

public class Link {
    private int linkTypeId;
    private int person1;
    private int person2;

    public int getLinkTypeId() {
        return linkTypeId;
    }

    public void setLinkTypeId(int linkTypeId) {
        this.linkTypeId = linkTypeId;
    }

    public int getPerson1() {
        return person1;
    }

    public void setPerson1(int person1) {
        this.person1 = person1;
    }

    public int getPerson2() {
        return person2;
    }

    public void setPerson2(int person2) {
        this.person2 = person2;
    }

    public Link(int linkTypeId, int person1, int person2) {
        this.linkTypeId = linkTypeId;
        this.person1 = person1;
        this.person2 = person2;
    }
}
