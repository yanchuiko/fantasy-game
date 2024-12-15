package org.fantasygame.model.entities.content.items.potions;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Poison Potion item in the game.
 */
public class PoisonPotion extends Potion {

    public PoisonPotion() {
        super(20);
    }

    @Override
    public String use(Player player) {
        player.setPowerPoints(player.getPowerPoints() - getPowerPoints());
        return "You have used a Poison Potion and lost " + getPowerPoints() + " power points.";
    }
}
