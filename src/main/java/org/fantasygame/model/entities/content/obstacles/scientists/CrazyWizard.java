package org.fantasygame.model.entities.content.obstacles.scientists;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents a Crazy Wizard obstacle in the game.
 */
public class CrazyWizard extends Scientist {

    public CrazyWizard() {
        super("Crazy Wizard");
    }

    @Override
    public String trigger(Player player) {
        player.setPowerPoints(player.getPowerPoints() - 50);
        setNeutralized(true); // Neutralize the obstacle
        return "The Crazy Wizard lectures about arcane magic, draining 50 Power Points!";
    }
}
