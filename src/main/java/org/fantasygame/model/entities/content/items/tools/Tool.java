package org.fantasygame.model.entities.content.items.tools;

import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.core.Player;

/**
 * Abstract class representing a Tool item in the game.
 */
public abstract class Tool extends Item {

    /**
     * Constructs a Tool with the specified name.
     *
     * @param name the name of the tool
     */
    public Tool(String name) {
        super(name);
    }

    /**
     * Uses the tool on the specified player.
     *
     * @param player the player using the tool
     * @return a string result of using the tool
     */
    @Override
    public abstract String use(Player player);
}
