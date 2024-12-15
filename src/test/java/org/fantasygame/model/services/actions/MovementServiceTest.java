package org.fantasygame.model.services.actions;

import org.fantasygame.model.entities.content.obstacles.Obstacle;
import org.fantasygame.model.entities.content.obstacles.traps.Trap;
import org.fantasygame.model.entities.core.Inventory;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.LevelService;
import org.fantasygame.model.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MovementServiceTest {

    private MovementService movementService;
    private Player mockPlayer;
    private Inventory mockInventory;
    private Level mockLevel;
    private LevelService mockLevelService;
    private RoomService mockRoomService;
    private Room[][] mockGrid;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize mocks
        mockPlayer = mock(Player.class);
        mockInventory = mock(Inventory.class);
        mockLevel = mock(Level.class);
        mockLevelService = mock(LevelService.class);
        mockRoomService = mock(RoomService.class);

        // Arrange: Set up player and inventory
        when(mockPlayer.getInventory()).thenReturn(mockInventory);

        // Arrange: Set up mock grid
        mockGrid = new Room[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mockGrid[i][j] = mock(Room.class);
            }
        }
        when(mockLevel.getGrid()).thenReturn(mockGrid);
        when(mockPlayer.getCurrentLevel()).thenReturn(mockLevel);

        // Arrange: Set player position
        when(mockPlayer.getRow()).thenReturn(2);
        when(mockPlayer.getCol()).thenReturn(3);

        // Initialize MovementService
        movementService = new MovementService(mockPlayer, mockLevelService, mockRoomService);
    }

    /**
     * Tests that a valid move updates the player's position, marks the room as visited and revealed,
     * and returns an empty feedback message.
     */
    @Test
    void testMoveValidDirection() {
        // Arrange: Valid room for the move
        String direction = "forward";
        Room newRoom = mock(Room.class);
        mockGrid[1][3] = newRoom; // Target position
        when(newRoom.getType()).thenReturn("empty");

        // Act: Execute the move
        String result = movementService.move(direction);

        // Assert: Verify correct interactions and result
        verify(mockPlayer).setRow(1);
        verify(mockPlayer).setCol(3);
        verify(newRoom).setVisited(true);
        verify(newRoom).setRevealed(true);
        verify(mockLevelService).handleRoomVisibility(eq(mockLevel), eq(mockPlayer));
        assertTrue(result.isEmpty(), "Feedback should be empty for a valid move with no obstacles.");
    }

    /**
     * Tests that attempting to move into a wall throws a RuntimeException.
     */
    @Test
    void testMoveIntoWall() {
        // Arrange: Set up a wall at the target position
        String direction = "forward";
        Room wallRoom = mock(Room.class);
        mockGrid[1][3] = wallRoom; // Target position
        when(wallRoom.getType()).thenReturn("wall");

        // Act: Attempt to move into the wall and handle exception
        RuntimeException exception = null;
        try {
            movementService.move(direction);
        } catch (RuntimeException e) {
            exception = e;
        }

        // Assert: Verify that an exception is thrown
        assertNotNull(exception, "Should throw an error for moving into a wall.");
        assertNotNull(exception.getMessage(), "Error message should be provided.");
    }

    /**
     * Tests that attempting to move out of bounds throws a RuntimeException.
     */
    @Test
    void testMoveOutOfBounds() {
        // Arrange: Simulate the player being at the top row
        String direction = "forward";
        when(mockPlayer.getRow()).thenReturn(0);
        when(mockPlayer.getCol()).thenReturn(3);

        // Act: Attempt to move out of bounds and handle exception
        RuntimeException exception = null;
        try {
            movementService.move(direction);
        } catch (RuntimeException e) {
            exception = e;
        }

        // Assert: Verify that an exception is thrown
        assertNotNull(exception, "Should throw an error for an out-of-bounds move.");
        assertNotNull(exception.getMessage(), "Error message should be provided.");
    }

    /**
     * Tests that moving into a room with an obstacle triggers the obstacle and removes it from the room.
     */
    @Test
    void testMoveHandlesObstacles() {
        // Arrange: Set up a room with an active obstacle
        String direction = "forward";
        Room newRoom = mock(Room.class);
        Obstacle mockObstacle = mock(Obstacle.class);
        List<Object> roomContent = List.of(mockObstacle);

        mockGrid[1][3] = newRoom; // Target position
        when(newRoom.getType()).thenReturn("empty");
        when(newRoom.getContent()).thenReturn(roomContent);
        when(mockObstacle.isNeutralized()).thenReturn(false).thenReturn(true);
        when(mockObstacle.trigger(mockPlayer)).thenReturn("You triggered an obstacle.");

        // Act: Execute the move
        String result = movementService.move(direction);

        // Assert: Verify the obstacle was triggered and removed
        verify(mockObstacle).trigger(mockPlayer);
        verify(mockRoomService).removeAllContent(newRoom, List.of(mockObstacle));
        assertEquals("You triggered an obstacle.", result, "Feedback should reflect the triggered obstacle.");
    }

    /**
     * Tests that a tool in the player's inventory neutralizes a trap in the room and returns the correct feedback.
     */
    @Test
    void testMoveActivatesTool() {
        // Arrange: Set up a room with a trap and a Hammer in inventory
        String direction = "forward";
        Room newRoom = mock(Room.class);
        Trap mockTrap = mock(Trap.class);
        List<Object> roomContent = new ArrayList<>();
        roomContent.add(mockTrap);

        mockGrid[1][3] = newRoom; // Target position
        when(newRoom.getType()).thenReturn("empty");
        when(newRoom.getContent()).thenReturn(roomContent);
        when(mockInventory.contains("Hammer")).thenReturn(true);

        // Act: Execute the move
        String result = movementService.move(direction);

        // Assert: Verify the trap was neutralized and feedback is correct
        verify(mockRoomService).removeAllContent(newRoom, List.of(mockTrap));
        assertEquals("Your Hammer smashed a trap that was in this room!", result);
    }

    /**
     * Tests that moving while the player is asleep wakes them up and returns the correct feedback.
     */
    @Test
    void testPlayerAsleep() {
        // Arrange: Simulate the player being asleep
        when(mockPlayer.isAsleep()).thenReturn(true);

        // Act: Execute the move
        String result = movementService.move("forward");

        // Assert: Verify the player is woken up and feedback is correct
        verify(mockPlayer).setAsleep(false);
        assertEquals("You were asleep and missed your turn!", result);
    }
}
