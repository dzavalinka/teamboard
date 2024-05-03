package com.dzavalinskii.board_controller;

public class Link {
    private LinkType linkType;
    private int person1;
    private int person2;

    public LinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
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

    public Link(LinkType linkType, int person1, int person2) {
        this.linkType = linkType;
        this.person1 = person1;
        this.person2 = person2;
    }
}
