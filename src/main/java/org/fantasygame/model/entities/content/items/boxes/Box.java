package org.fantasygame.model.entities.content.items.boxes;

import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.core.Player;

/**
 * Abstract class representing a Box item in the game.
 */
public abstract class Box extends Item {

    /**
     * Constructs a Box item with the specified name.
     *
     * @param name the name of the Box item
     */
    public Box(String name) {
        super(name);
    }

    /**
     * Uses the Box item on the specified player.
     *
     * @param player the player on whom the Box item is used
     * @return a string describing the result of using the Box item
     */
    @Override
    public abstract String use(Player player);
}