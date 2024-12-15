package org.fantasygame.model.entities.content.items.food;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Pizza food item in the game.
 */
public class Pizza extends Food {

    public Pizza() {
        super("Pizza", 30);
    }

    @Override
    public String use(Player player) {
        player.setPowerPoints(player.getPowerPoints() + powerPoints);
        return null;
    }
}
