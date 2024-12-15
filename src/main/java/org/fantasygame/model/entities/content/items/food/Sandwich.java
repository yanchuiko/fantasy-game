package org.fantasygame.model.entities.content.items.food;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Sandwich food item in the game.
 */
public class Sandwich extends Food {

    public Sandwich() {
        super("Sandwich", 15);
    }

    @Override
    public String use(Player player) {
        player.setPowerPoints(player.getPowerPoints() + powerPoints);
        return null;
    }
}
