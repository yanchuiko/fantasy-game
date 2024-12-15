package org.fantasygame.model.services.actions;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.PlayerService;

public class LookAroundService {

    private final PlayerService playerService;

    /**
     * Constructs a LookAroundService with the necessary dependencies.
     *
     * @param playerService the PlayerService instance
     */
    public LookAroundService(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Allows the player to look around in the current room and provides a description of its contents.
     *
     * @return a description of the items in the room
     */
    public String lookAround() {
        Room playerCurrentRoom = playerService.getPlayerCurrentRoom();

        // Check if the room is null or has no content.
        if (playerCurrentRoom == null || playerCurrentRoom.getContent().isEmpty()) {
            ErrorHandler.throwError(600);
        }

        // Build a description of the items in the room.
        StringBuilder description = new StringBuilder();
        for (Object item : playerCurrentRoom.getContent()) {
            description.append(item.toString());
            description.append(", ");
        }

        // Remove the last comma and space.
        if (!description.isEmpty()) {
            description.setLength(description.length() - 2);
        }

        return description.toString();
    }
}
