package org.fantasygame.model.entities.content.items.potions;

import org.fantasygame.model.entities.content.items.Item;

/**
 * Abstract class representing a Potion item in the game.
 */
public abstract class Potion extends Item {

    protected final int powerPoints;

    /**
     * Constructs a Potion with the specified power points.
     *
     * @param powerPoints the amount of power points the potion provides
     */
    public Potion(int powerPoints) {
        super("Potion"); // All potions are named "Potion" (to make it hidden from the player)
        this.powerPoints = powerPoints;
    }

    /**
     * Gets the power points of the potion.
     *
     * @return the power points of the potion
     */
    public int getPowerPoints() {
        return powerPoints;
    }
}
