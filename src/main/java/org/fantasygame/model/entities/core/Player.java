package org.fantasygame.model.entities.core;

/**
 * Represents a Player in the game.
 */
public class Player {
    private int row;
    private int col;
    private boolean asleep = false;
    private int powerPoints;
    private Inventory inventory;
    private Level currentLevel;

    /**
     * Constructs a Player with the specified row, column, and power points.
     *
     * @param row the row position of the player
     * @param col the column position of the player
     * @param powerPoints the initial power points of the player
     */
    public Player(int row, int col, int powerPoints) {
        this.row = row;
        this.col = col;
        this.powerPoints = powerPoints;
        this.inventory = new Inventory();
        this.currentLevel = null;
    }

    /**
     * Returns the row position of the player.
     *
     * @return the row position
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row position of the player.
     *
     * @param row the new row position
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the column position of the player.
     *
     * @return the column position
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the column position of the player.
     *
     * @param col the new column position
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Returns the power points of the player.
     *
     * @return the power points
     */
    public int getPowerPoints() {
        return powerPoints;
    }

    /**
     * Sets the power points of the player.
     *
     * @param powerPoints the new power points
     */
    public void setPowerPoints(int powerPoints) {
        this.powerPoints = powerPoints;
    }

    /**
     * Returns the inventory of the player.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns whether the player is asleep.
     *
     * @return true if the player is asleep, false otherwise
     */
    public boolean isAsleep() {
        return asleep;
    }

    /**
     * Sets the asleep status of the player.
     *
     * @param asleep true if the player is asleep, false otherwise
     */
    public void setAsleep(boolean asleep) {
        this.asleep = asleep;
    }

    /**
     * Returns the current level of the player.
     *
     * @return the current level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets the current level of the player.
     *
     * @param currentLevel the new current level
     */
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }
}
