package org.fantasygame.model.services.actions;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.PlayerService;
import org.fantasygame.model.services.RoomService;

public class DropService {

    private final Player player;
    private final PlayerService playerService;
    private final RoomService roomService;

    /**
     * Constructs a DropService with the necessary dependencies.
     *
     * @param player        the player entity
     * @param playerService the service providing player-related utilities
     * @param roomService   the room service to handle room-related operations
     */
    public DropService(Player player, PlayerService playerService, RoomService roomService) {
        this.player = player;
        this.playerService = playerService;
        this.roomService = roomService;
    }

    /**
     * Allows the player to drop an item from their inventory into the current room.
     *
     * @param itemName the name of the item to drop
     * @return a feedback message indicating the item was dropped
     */
    public String drop(String itemName) {
        Room playerCurrentRoom = playerService.getPlayerCurrentRoom();

        // Ensure the player has the item in their inventory.
        if (!player.getInventory().contains(itemName)) {
            ErrorHandler.throwError(301);
        }

        // Remove the item from the inventory and add it to the room.
        Item item = player.getInventory().removeItem(itemName);
        roomService.addContent(playerCurrentRoom, item);

        return "You dropped " + item.getName() + ".";
    }
}
