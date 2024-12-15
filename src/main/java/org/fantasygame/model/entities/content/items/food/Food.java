package org.fantasygame.model.entities.content.items.food;

import org.fantasygame.model.entities.content.items.Item;

/**
 * Abstract class representing a Food item in the game.
 */
public abstract class Food extends Item {

    protected int powerPoints;

    /**
     * Constructs a Food item with the specified name and power points.
     *
     * @param name the name of the Food item
     * @param powerPoints the power points of the Food item
     */
    public Food(String name, int powerPoints) {
        super(name);
        this.powerPoints = powerPoints;
    }

    /**
     * Gets the power points of the Food item.
     *
     * @return the power points of the Food item
     */
    public int getPowerPoints() {
        return powerPoints;
    }
}
