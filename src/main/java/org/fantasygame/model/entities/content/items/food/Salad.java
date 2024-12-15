package org.fantasygame.model.entities.content.items.food;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Salad food item in the game.
 */
public class Salad extends Food {

    public Salad() {
        super("Salad", 5);
    }

    @Override
    public String use(Player player) {
        player.setPowerPoints(player.getPowerPoints() + powerPoints);
        return null;
    }
}
