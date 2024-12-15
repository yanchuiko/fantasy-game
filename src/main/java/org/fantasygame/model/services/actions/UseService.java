package org.fantasygame.model.services.actions;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.content.items.spells.FreezeSpell;
import org.fantasygame.model.entities.content.items.spells.Spell;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.services.RoomService;

public class UseService {

    private final Player player;
    private final RoomService roomService;

    /**
     * Constructs a UseService with the necessary dependencies.
     *
     * @param player       the player instance
     * @param roomService  the room service to handle room-related operations
     */
    public UseService(Player player, RoomService roomService) {
        this.player = player;
        this.roomService = roomService;
    }

    /**
     * Allows the player to use a spell from their inventory.
     *
     * @param spellName the name of the spell to use
     * @return a feedback message indicating the spell was used
     */
    public String use(String spellName) {
        Item item = player.getInventory().getItem(spellName);

        // Ensure the item is a spell type.
        if (!(item instanceof Spell)) {
            ErrorHandler.throwError(802);
        }

        Spell spell = (Spell) item;

        // Special handling for the Freeze Spell, which automatically neutralizes obstacles.
        if (spell instanceof FreezeSpell) {
            spell.use(player); // Use the Freeze Spell
            roomService.removeAllObstacles(player); // Remove all obstacles in the player's room
            ErrorHandler.throwError(901);
        }

        // Use the spell and remove it from the inventory.
        spell.use(player);
        player.getInventory().removeItem(spellName);

        return "You used the " + spell.getName() + "!";
    }
}
