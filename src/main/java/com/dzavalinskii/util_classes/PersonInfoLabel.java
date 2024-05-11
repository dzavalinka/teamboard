package com.dzavalinskii.util_classes;

import javafx.scene.control.Label;

public class PersonInfoLabel extends Label {
    public final long personInfoId;

    public PersonInfoLabel(String text, long personInfoId) {
        super(text);
        this.personInfoId = personInfoId;
    }
}
