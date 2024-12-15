package org.fantasygame.model.entities.content.items.spells;

import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.content.obstacles.Obstacle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Freeze Spell item in the game.
 */
public class FreezeSpell extends Spell {

    public FreezeSpell() {
        super("Freeze Spell");
    }

    @Override
    public String use(Player player) {
        // Get all obstacles in the current room
        List<Object> obstacles = new ArrayList<>();

        // Add all obstacles in the current room to the list
        for (Object content : player.getCurrentLevel().getRoom(player.getRow(), player.getCol()).getContent()) {
            if (content instanceof Obstacle) {
                obstacles.add(content);
            }
        }

        // Neutralize all obstacles in the current room
        if (!obstacles.isEmpty()) {
            for (Object item : obstacles) {
                if (item instanceof Obstacle obstacle) {
                    obstacle.setNeutralized(true);
                }
            }
        }

        return null;
    }
}
