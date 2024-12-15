package org.fantasygame.model.entities.content.items;

import org.fantasygame.model.entities.core.Player;

/**
 * Abstract class representing an item in the game.
 */
public abstract class Item {
    private final String name;

    /**
     * Constructor for creating an item with a specified name.
     *
     * @param name the name of the item
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the item.
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Abstract method to use the item on a player.
     *
     * @param player the player using the item
     * @return a string describing the effect of the item
     */
    public abstract String use(Player player);

    /**
     * Returns a string representation of the item.
     *
     * @return the name of the item
     */
    @Override
    public String toString() {
        return name;
    }
}
