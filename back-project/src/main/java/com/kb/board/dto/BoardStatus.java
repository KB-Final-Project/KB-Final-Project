package com.kb.board.dto;

public enum BoardStatus {
    ACTIVE("y"),
    INACTIVE("n");

    private final String value;

    BoardStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BoardStatus fromValue(String value) {
        for (BoardStatus status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}
