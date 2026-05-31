package com.demo.travelcardsystem.constant;

public enum Zone {
    ONE("Zone 1"),
    TWO("Zone 2"),
    THREE("Zone 3");

    private final String displayLabel;

    Zone(String displayLabel) {
        this.displayLabel = displayLabel;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }
}
