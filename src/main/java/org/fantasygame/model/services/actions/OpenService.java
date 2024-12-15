package org.fantasygame.model.services.actions;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.content.items.boxes.Box;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.RoomService;
import org.fantasygame.model.services.PlayerService;

public class OpenService {

    private final Player player;
    private final PlayerService playerService;
    private final RoomService roomService;

    /**
     * Constructs the OpenService with the necessary dependencies.
     *
     * @param player        the Player entity
     * @param playerService the PlayerService instance
     * @param roomService   the RoomService instance
     */
    public OpenService(Player player, PlayerService playerService, RoomService roomService) {
        this.player = player;
        this.playerService = playerService;
        this.roomService = roomService;
    }

    /**
     * Opens a box in the current room using a tool (Spanner).
     *
     * @param boxName the name of the box to open
     * @return a feedback message indicating the result of opening the box
     */
    public String open(String boxName) {
        Room playerCurrentRoom = playerService.getPlayerCurrentRoom();
        Item item = playerService.findItemInRoom(playerCurrentRoom, boxName);

        // Ensure the item is a box
        if (!(item instanceof Box)) {
            ErrorHandler.throwError(601);
        }

        // Ensure the player has the necessary tool (Spanner) to open the box
        if (!player.getInventory().contains("Spanner")) {
            ErrorHandler.throwError(900);
        }

        Box box = (Box) item;
        String result = box.use(player);

        // Remove the box from the room
        roomService.removeContent(playerCurrentRoom, box);

        return result;
    }
}
