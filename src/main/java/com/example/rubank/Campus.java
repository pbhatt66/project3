package com.example.rubank;

/**
 * Campus enum class represents a campus as a campus code (0, 1, or 2).
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public enum Campus {
    NEW_BRUNSWICK(0),
    NEWARK(1),
    CAMDEN(2);

    private final int campusCode;

    /**
     * Constructs a Campus object with the given campusCode
     * @param campusCode the campus code
     */
    Campus(int campusCode) {
        this.campusCode = campusCode;
    }

    /**
     * Gets the campus code.
     * @return the campus code
     */
    public int getCampusCode() {
        return campusCode;
    }
}