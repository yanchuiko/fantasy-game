package org.fantasygame.model.entities.content.obstacles.scientists;

import org.fantasygame.model.entities.core.Player;

/**
 * Represents an Evil Chemist obstacle in the game.
 */
public class EvilChemist extends Scientist {

    public EvilChemist() {
        super("Evil Chemist");
    }

    @Override
    public String trigger(Player player) {
        player.setPowerPoints(player.getPowerPoints() - 40);
        setNeutralized(true); // Neutralize the obstacle
        return "The Evil Chemist tested his new potion on you, draining 40 Power Points!";
    }
}
