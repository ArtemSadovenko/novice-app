package com.novice.debatenovice.enums;

public enum PLACE {
    FIRST(3),
    SECOND(2),
    THIRD(1),
    FOURTH(0);

    private final int points;

    PLACE(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
