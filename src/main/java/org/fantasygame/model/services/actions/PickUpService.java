package org.fantasygame.model.services.actions;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.content.items.boxes.Box;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.PlayerService;
import org.fantasygame.model.services.RoomService;

public class PickUpService {

    private final Player player;
    private final PlayerService playerService;
    private final RoomService roomService;

    /**
     * Constructs a PickUpService with the necessary dependencies.
     *
     * @param player        the player instance
     * @param playerService the service to manage the player state
     * @param roomService   the service to manage room content
     */
    public PickUpService(Player player, PlayerService playerService, RoomService roomService) {
        this.player = player;
        this.playerService = playerService;
        this.roomService = roomService;
    }

    /**
     * Handles the action of picking up an item from the current room.
     *
     * @param itemName the name of the item to pick up
     * @return a feedback message indicating the result of the action
     */
    public String pickUp(String itemName) {
        Room playerCurrentRoom = playerService.getPlayerCurrentRoom();

        // Check if the room is null
        if (playerCurrentRoom == null) {
            ErrorHandler.throwError(700);
        }

        // Find the item in the room
        Item item = playerService.findItemInRoom(playerCurrentRoom, itemName);

        // If the item is not found, throw an error
        if (item == null) {
            ErrorHandler.throwError(701);
        }

        // Check if the item is a box, which cannot be picked up
        if (item instanceof Box) {
            ErrorHandler.throwError(702);
        }

        // Add the item to the player's inventory
        if (!player.getInventory().addItem(item)) {
            ErrorHandler.throwError(703);
        }

        // Remove the item from the room
        roomService.removeContent(playerCurrentRoom, item);
        return "You picked up " + item.getName() + ".";
    }
}
