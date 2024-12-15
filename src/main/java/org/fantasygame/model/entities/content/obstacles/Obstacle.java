package org.fantasygame.model.entities.content.obstacles;

import org.fantasygame.model.entities.core.Player;

/**
 * Abstract class representing an Obstacle in the game.
 */
public abstract class Obstacle {
    private final String name;
    private boolean neutralized = false;

    /**
     * Constructs an Obstacle with the specified name.
     *
     * @param name the name of the obstacle
     */
    public Obstacle(String name) {
        this.name = name;
    }

    /**
     * Triggers the obstacle's effect on the specified player.
     *
     * @param player the player triggering the obstacle
     * @return a string result of triggering the obstacle
     */
    public abstract String trigger(Player player);

    /**
     * Gets the name of the obstacle.
     *
     * @return the name of the obstacle
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the neutralized state of the obstacle.
     *
     * @param neutralized the new neutralized state
     */
    public void setNeutralized(boolean neutralized) {
        this.neutralized = neutralized;
    }

    /**
     * Checks if the obstacle is neutralized.
     *
     * @return true if the obstacle is neutralized, false otherwise
     */
    public boolean isNeutralized() {
        return neutralized;
    }

    /**
     * Returns a string representation of the obstacle.
     *
     * @return the name of the obstacle
     */
    @Override
    public String toString() {
        return name;
    }
}
