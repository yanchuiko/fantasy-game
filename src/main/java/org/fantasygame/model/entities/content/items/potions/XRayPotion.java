package org.fantasygame.model.entities.content.items.potions;

import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;

import java.util.Random;

/**
 * Represents a XRay Potion item in the game.
 */
public class XRayPotion extends Potion {

    /**
     * Constructor for the XRayPotion. Calls the superclass constructor with 0 power points,
     * as the potion does not affect the player's power points directly.
     */
    public XRayPotion() {
        super(0);
    }

    @Override
    public String use(Player player) {
        return revealRandomRoom(player.getCurrentLevel());
    }

    /**
     * Reveals a random room in the given level, making it visible, and returns a message
     * describing the revealed room's contents.
     *
     * @param level the level in which to reveal a room
     * @return a message describing the revealed room and its content
     */
    private String revealRandomRoom(Level level) {
        Room[][] grid = level.getGrid(); // Get the grid of rooms for the current level
        int rows = grid.length; // Get the number of rows in the grid
        int cols = grid[0].length; // Get the number of columns in the grid

        Room randomRoom;
        do {
            // Randomly select a row and column in the grid
            int randomRow = new Random().nextInt(rows);
            int randomCol = new Random().nextInt(cols);
            randomRoom = level.getRoom(randomRow, randomCol); // Get the room at the random position
        } while (randomRoom == null || "wall".equals(randomRoom.getType())
                || "entrance".equals(randomRoom.getType())
                || "exit".equals(randomRoom.getType())); // Ensure the room is not a wall, entrance, or exit

        randomRoom.setRevealed(true); // Mark the room as revealed

        // Building the message with the room coordinates and content
        StringBuilder message = new StringBuilder();
        message.append("You drank the X-Ray Potion and revealed a hidden room!\n");
        message.append("Room (").append(randomRoom.getRow() + 1).append(", ").append(randomRoom.getCol() + 1).append(") - ");

        // Add information about the content of the revealed room
        if (!randomRoom.getContent().isEmpty()) {
            message.append("Contains: ");
            for (Object content : randomRoom.getContent()) {
                message.append(content.toString()).append(", "); // Append each content item's description
            }
            // Remove the last comma and space
            message.setLength(message.length() - 2);
        } else {
            message.append("No content.");
        }

        return message.toString(); // Return the final message
    }
}