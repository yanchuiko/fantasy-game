package org.fantasygame.model.entities.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Room in the game level.
 */
public class Room {
    private final int row;
    private final int col;
    private String type;
    private final List<Object> content;
    private boolean visited;
    private boolean revealed;

    /**
     * Constructs a Room with the specified row, column, and type.
     *
     * @param row  the row position of the room
     * @param col  the column position of the room
     * @param type the type of the room
     */
    public Room(int row, int col, String type) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.content = new ArrayList<>();
        this.visited = false;
        this.revealed = false;
    }

    /**
     * Returns the row position of the room.
     *
     * @return the row position
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column position of the room.
     *
     * @return the column position
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the type of the room.
     *
     * @return the type of the room
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the room. (for testing purposes)
     *
     * @param type the new type of the room
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the content of the room.
     *
     * @return a list of objects representing the content of the room
     */
    public List<Object> getContent() {
        return content;
    }

    /**
     * Returns whether the room is revealed.
     *
     * @return true if the room is revealed, false otherwise
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * Sets the visited status of the room.
     *
     * @param visited true if the room has been visited, false otherwise
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Sets the revealed status of the room.
     *
     * @param revealed true if the room is revealed, false otherwise
     */
    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}