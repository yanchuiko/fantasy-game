package org.fantasygame.model.entities.content.items.potions;

import org.fantasygame.model.entities.core.Player;

/**
 * A class representing a Sleep Potion item in the game.
 */
public class SleepPotion extends Potion {

    public SleepPotion() {
        super(30);
    }

    @Override
    public String use(Player player) {
        // If the player has an Alarm Clock, they will wake up immediately and not lose any Power Points
        if (player.getInventory().contains("Alarm Clock")) {
            player.setAsleep(false);
            return "The Sleep Potion made you drowsy, but your Alarm Clock woke you up immediately! No Power Points were lost.";
        }

        // If the player does not have an Alarm Clock, they will fall asleep and lose Power Points
        player.setAsleep(true);
        player.setPowerPoints(player.getPowerPoints() - getPowerPoints());
        return "You have used a Sleep Potion and fallen asleep. You lost " + getPowerPoints() + " Power Points.";
    }
}
