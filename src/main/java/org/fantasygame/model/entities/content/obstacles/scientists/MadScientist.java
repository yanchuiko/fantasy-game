package org.fantasygame.model.entities.content.obstacles.scientists;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Mad Scientist obstacle in the game.
 */
public class MadScientist extends Scientist {

    public MadScientist() {
        super("Mad Scientist");
    }

    @Override
    public String trigger(Player player) {
        player.setPowerPoints(player.getPowerPoints() - 30);
        setNeutralized(true); // Neutralize the obstacle
        return "The Mad Scientist demands money for research, costing you 30 Power Points!";
    }
}
