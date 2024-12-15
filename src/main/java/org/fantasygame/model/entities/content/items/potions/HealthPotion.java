package org.fantasygame.model.entities.content.items.potions;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Health Potion item in the game.
 */
public class HealthPotion extends Potion {

    public HealthPotion() {
        super(20);
    }

    @Override
    public String use(Player player) {
        player.setPowerPoints(player.getPowerPoints() + getPowerPoints());
        return "You have used a Health Potion and gained " + getPowerPoints() + " power points.";
    }
}
