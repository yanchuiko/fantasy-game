package org.fantasygame.model.entities.content.obstacles.traps;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Fire Trap obstacle in the game.
 */
public class FireTrap extends Trap {

    public FireTrap() {
        super("Fire Trap");
    }

    @Override
    public String trigger(Player player) {
        player.setPowerPoints(player.getPowerPoints() - 25);
        setNeutralized(true); // Neutralize the obstacle
        return "You triggered a Fire Trap and lost 25 Power Points!";
    }
}
