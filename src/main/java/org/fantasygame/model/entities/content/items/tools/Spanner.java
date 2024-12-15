package org.fantasygame.model.entities.content.items.tools;

import org.fantasygame.model.entities.content.items.boxes.Box;
import org.fantasygame.model.entities.core.Player;

import java.util.List;

/**
 * Represents a Spanner item in the game.
 */
public class Spanner extends Tool {

    public Spanner() {
        super("Spanner");
    }

    @Override
    public String use(Player player) {
        // Get the contents of the room the player is in
        List<Object> contents = player.getCurrentLevel().getRoom(player.getRow(), player.getCol()).getContent();

        // Use the spanner on any boxes in the room
        for (Object content : contents) {
            if (content instanceof Box box) {
                box.use(player);
            }
        }

        return null;
    }
}
