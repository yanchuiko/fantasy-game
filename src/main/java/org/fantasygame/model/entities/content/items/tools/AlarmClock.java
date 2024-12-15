package org.fantasygame.model.entities.content.items.tools;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Alarm Clock item in the game.
 */
public class AlarmClock extends Tool {
    public AlarmClock() {
        super("Alarm Clock");
    }

    @Override
    public String use(Player player) {
        // Awake the player
        player.setAsleep(false);
        return null;
    }
}
