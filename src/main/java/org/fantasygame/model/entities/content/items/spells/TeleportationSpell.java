package org.fantasygame.model.entities.content.items.spells;

import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;

import java.util.Random;

/**
 * Represents a Teleportation Spell item in the game.
 */
public class TeleportationSpell extends Spell {

    private static Random randomInstance = new Random(); // Using Random instance for unit tests

    public TeleportationSpell() {
        super("Teleportation Spell");
    }

    @Override
    public String use(Player player) {
        // Get the current level
        Level currentLevel = player.getCurrentLevel();

        // Get the grid of rooms
        Room[][] grid = currentLevel.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        Room randomRoom;

        // Find a random room that is not a wall, exit, or entrance
        do {
            int randomRow = randomInstance.nextInt(rows);
            int randomCol = randomInstance.nextInt(cols);
            randomRoom = currentLevel.getRoom(randomRow, randomCol);
        } while (randomRoom == null || "wall".equals(randomRoom.getType()) ||
                "exit".equals(randomRoom.getType()) || "entrance".equals(randomRoom.getType()));

        // Move the player to the random room
        player.setRow(randomRoom.getRow());
        player.setCol(randomRoom.getCol());

        // Set the random room as visited and revealed
        randomRoom.setVisited(true);
        randomRoom.setRevealed(true);
        return null;
    }

    public static void setRandomInstance(Random random) {
        randomInstance = random;
    }
}
