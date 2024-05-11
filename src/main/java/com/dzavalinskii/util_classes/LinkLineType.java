package com.dzavalinskii.util_classes;

public enum LinkLineType {
    SOLID("Сплошная"),
    DOTTED("Точечная"),
    DASHED("Пунктир"),
    DOTDASH("Точка-пунктир");

    private String label;

    LinkLineType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
