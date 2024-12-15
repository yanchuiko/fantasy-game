package org.fantasygame.model.entities.content.obstacles.traps;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Poison Trap obstacle in the game.
 */
public class PoisonTrap extends Trap {

    public PoisonTrap() {
        super("Poison Trap");
    }

    @Override
    public String trigger(Player player) {
        player.setPowerPoints(player.getPowerPoints() - 30);
        setNeutralized(true); // Neutralize the obstacle
        return "You triggered a Poison Trap and lost 30 Power Points!";
    }
}
