package org.fantasygame.model.entities.content.items.food;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Cake food item in the game.
 */
public class Cake extends Food {

    public Cake() {
        super("Cake", 10);
    }

    @Override
    public String use(Player player) {
        player.setPowerPoints(player.getPowerPoints() + powerPoints);
        return null;
    }
}
