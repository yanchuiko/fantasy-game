package org.fantasygame.model.entities.content.obstacles.scientists;

import org.fantasygame.model.entities.content.obstacles.Obstacle;
import org.fantasygame.model.entities.core.Player;

/**
 * Abstract class representing a Scientist in the game.
 */
public abstract class Scientist extends Obstacle {

    /**
     * Constructs a Scientist with the specified name.
     *
     * @param name the name of the scientist
     */
    public Scientist(String name) {
        super(name);
    }

    /**
     * Triggers the scientist's effect on the specified player.
     *
     * @param player the player triggering the scientist
     * @return a string result of triggering the scientist
     */
    @Override
    public abstract String trigger(Player player);
}
