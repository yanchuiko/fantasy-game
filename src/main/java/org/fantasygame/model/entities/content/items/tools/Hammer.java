package org.fantasygame.model.entities.content.items.tools;

import org.fantasygame.model.entities.content.obstacles.traps.Trap;
import org.fantasygame.model.entities.core.Player;

import java.util.List;

/**
 * Represents a Hammer item in the game.
 */
public class Hammer extends Tool {

    public Hammer() {
        super("Hammer");
    }

    @Override
    public String use(Player player) {
        // Get the contents of the room the player is in
        List<Object> contents = player.getCurrentLevel().getRoom(player.getRow(), player.getCol()).getContent();

        // Remove any traps in the room
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i) instanceof Trap) {
                contents.remove(i);
                i--;
            }
        }
        return null;
    }
}
