package org.fantasygame.model.entities.content.items.food;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Soup food item in the game.
 */
public class Soup extends Food {

    public Soup() {
        super("Soup", 20);
    }

    @Override
    public String use(Player player) {
        player.setPowerPoints(player.getPowerPoints() + powerPoints);
        return null;
    }
}
