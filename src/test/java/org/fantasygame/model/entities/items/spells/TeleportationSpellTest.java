package org.fantasygame.model.entities.items.spells;

import org.fantasygame.model.entities.content.items.spells.TeleportationSpell;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.mockito.Mockito.*;

class TeleportationSpellTest {

    private TeleportationSpell teleportationSpell;
    private Player player;
    private Level level;
    private Room[][] grid;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize TeleportationSpell and mock dependencies
        teleportationSpell = new TeleportationSpell();
        player = mock(Player.class);
        level = mock(Level.class);
        grid = new Room[3][3];

        // Arrange: Create mock rooms with default properties
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = mock(Room.class);
                when(grid[i][j].getRow()).thenReturn(i);
                when(grid[i][j].getCol()).thenReturn(j);
                when(grid[i][j].getType()).thenReturn("normal");
            }
        }

        // Arrange: Set special room types
        when(grid[0][0].getType()).thenReturn("wall");
        when(grid[1][1].getType()).thenReturn("exit");
        when(grid[2][2].getType()).thenReturn("entrance");

        // Arrange: Mock level grid and room fetching
        when(level.getGrid()).thenReturn(grid);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                when(level.getRoom(i, j)).thenReturn(grid[i][j]);
            }
        }

        // Arrange: Set player's current level
        when(player.getCurrentLevel()).thenReturn(level);
    }

    /**
     * Tests that the TeleportationSpell teleports the player to a valid room
     * and marks the room as visited and revealed.
     */
    @Test
    void testUseTeleportsToValidRoom() {
        // Arrange: Mock Random to select indices (1, 1), pointing to a "normal" room
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(3)).thenReturn(1, 1); // Returns 1 for both row and column
        when(grid[1][1].getType()).thenReturn("normal"); // Ensure (1, 1) is "normal"
        TeleportationSpell.setRandomInstance(mockRandom);

        // Act: Use the TeleportationSpell
        teleportationSpell.use(player);

        // Assert: Verify the player is teleported to (1, 1)
        verify(player).setRow(1);
        verify(player).setCol(1);

        // Assert: Verify the room (1, 1) is marked as visited and revealed
        verify(grid[1][1]).setVisited(true);
        verify(grid[1][1]).setRevealed(true);
    }

    /**
     * Tests that the TeleportationSpell avoids invalid rooms (wall, entrance, exit)
     * and teleports the player to the first valid room.
     */
    @Test
    void testUseAvoidsInvalidRoomsAndTeleportsToValidRoom() {
        // Arrange: Mock Random to simulate room selection with invalid rooms first
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(3))
                .thenReturn(0, 0) // Wall room
                .thenReturn(1, 1) // Exit room
                .thenReturn(2, 2) // Entrance room
                .thenReturn(1, 2); // Valid room
        TeleportationSpell.setRandomInstance(mockRandom);

        // Act: Use the TeleportationSpell
        teleportationSpell.use(player);

        // Assert: Verify the player is teleported to the valid room (1, 2)
        verify(player).setRow(1);
        verify(player).setCol(2);

        // Assert: Verify the valid room (1, 2) is marked as visited and revealed
        verify(grid[1][2]).setVisited(true);
        verify(grid[1][2]).setRevealed(true);
    }
}
