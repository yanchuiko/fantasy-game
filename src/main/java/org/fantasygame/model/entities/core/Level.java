package org.fantasygame.model.entities.core;

/**
 * Represents a Level in the game.
 */
public class Level {
    private final int levelNumber;
    private final Room[][] grid;
    private final int[] entrance;
    private final int[] exit;

    /**
     * Constructs a Level with the specified level number, grid, entrance, and exit.
     *
     * @param levelNumber the number of the level
     * @param grid the grid of rooms in the level
     * @param entrance the coordinates of the entrance
     * @param exit the coordinates of the exit
     */
    public Level(int levelNumber, Room[][] grid, int[] entrance, int[] exit) {
        this.levelNumber = levelNumber;
        this.grid = grid;
        this.entrance = entrance;
        this.exit = exit;
    }

    /**
     * Returns the level number.
     *
     * @return the level number
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Returns the grid of rooms in the level.
     *
     * @return the grid of rooms
     */
    public Room[][] getGrid() {
        return grid;
    }

    /**
     * Returns the coordinates of the entrance.
     *
     * @return the entrance coordinates
     */
    public int[] getEntrance() {
        return entrance;
    }

    /**
     * Returns the coordinates of the exit.
     *
     * @return the exit coordinates
     */
    public int[] getExit() {
        return exit;
    }

    /**
     * Returns the room at the specified row and column.
     *
     * @param row the row of the room
     * @param col the column of the room
     * @return the room at the specified coordinates, or null if out of bounds
     */
    public Room getRoom(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return null;
        }
        return grid[row][col];
    }
}
