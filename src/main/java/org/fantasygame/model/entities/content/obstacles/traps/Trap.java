package org.fantasygame.model.entities.content.obstacles.traps;

import org.fantasygame.model.entities.content.obstacles.Obstacle;
import org.fantasygame.model.entities.core.Player;

/**
 * Abstract class representing a Trap in the game.
 */
public abstract class Trap extends Obstacle {

    /**
     * Constructs a Trap with the specified name.
     *
     * @param name the name of the trap
     */
    public Trap(String name) {
        super(name);
    }

    /**
     * Triggers the trap's effect on the specified player.
     *
     * @param player the player triggering the trap
     * @return a string result of triggering the trap
     */
    @Override
    public abstract String trigger(Player player);
}
