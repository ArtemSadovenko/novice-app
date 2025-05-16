package com.novice.debatenovice.enums;

public enum POSITION {
    OPENING_PROPOSITION(1),
    OPENING_OPPOSITION(2),
    CLOSING_PROPOSITION(3),
    CLOSING_OPPOSITION(4);

    private final int value;

    POSITION(int value) {
        this.value = value;
    }

    public static POSITION fromInt(int value) {
        for (POSITION e : POSITION.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    public int getValue() {
        return value;
    }

}
