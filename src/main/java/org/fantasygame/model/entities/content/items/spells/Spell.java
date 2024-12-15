package org.fantasygame.model.entities.content.items.spells;

import org.fantasygame.model.entities.content.items.Item;

/**
 * Abstract class representing a Spell item in the game.
 */
public abstract class Spell extends Item {

    /**
     * Constructs a Spell with the specified name.
     *
     * @param name the name of the spell
     */
    public Spell(String name) {
        super(name);
    }
}
