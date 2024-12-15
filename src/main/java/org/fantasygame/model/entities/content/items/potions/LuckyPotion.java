package org.fantasygame.model.entities.content.items.potions;

import org.fantasygame.model.entities.core.Player;

import java.util.Random;

/**
 * Represents a Lucky Potion item in the game.
 */
public class LuckyPotion extends Potion {
    private final Random random;

    public LuckyPotion() {
        this(new Random());
    }

    public LuckyPotion(Random random) {
        super(50);
        this.random = random;
    }

    @Override
    public String use(Player player) {
        boolean wasLucky = random.nextBoolean();
        if (wasLucky) { // If the player was lucky, they gain power points
            player.setPowerPoints(player.getPowerPoints() + getPowerPoints());
            return "You have used a Lucky Potion and gained " + getPowerPoints() + " power points.";
        } else { // If the player was not lucky, they lose power points
            player.setPowerPoints(player.getPowerPoints() - getPowerPoints());
            return "You have used a Lucky Potion and lost " + getPowerPoints() + " power points.";
        }
    }
}
