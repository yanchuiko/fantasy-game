package org.fantasygame.model.entities.content.obstacles.traps;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Needle Trap obstacle in the game.
 */
public class NeedleTrap extends Trap {

    public NeedleTrap() {
        super("Needle Trap");
    }

    @Override
    public String trigger(Player player) {
        player.setPowerPoints(player.getPowerPoints() - 20);
        setNeutralized(true); // Neutralize the obstacle
        return "You triggered a Needle Trap and lost 20 Power Points!";
    }
}
