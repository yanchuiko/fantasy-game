package org.fantasygame.model.services;

import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceTest {

    private LevelService mockLevelService;
    private RoomService mockRoomService;
    private Player mockPlayer;
    private Level mockLevel;
    private Room mockRoom;
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize dependencies
        mockLevelService = mock(LevelService.class);
        mockRoomService = mock(RoomService.class);
        mockPlayer = mock(Player.class);
        mockLevel = mock(Level.class);
        mockRoom = mock(Room.class);

        // Arrange: Mock player position and current level
        when(mockPlayer.getRow()).thenReturn(2);
        when(mockPlayer.getCol()).thenReturn(3);
        when(mockPlayer.getCurrentLevel()).thenReturn(mockLevel);

        // Arrange: Mock the player's current room
        when(mockLevel.getRoom(2, 3)).thenReturn(mockRoom);

        // Arrange: Initialize PlayerService
        playerService = new PlayerService(mockPlayer, mockLevel, mockLevelService, mockRoomService);
    }

    /**
     * Tests that an item is found in the room if it exists.
     */
    @Test
    void testFindItemInRoomItemExists() {
        // Arrange: Mock an item in the room
        String itemName = "Cake";
        Item mockItem = mock(Item.class);
        when(mockItem.getName()).thenReturn(itemName);
        when(mockRoom.getContent()).thenReturn(List.of(mockItem));

        // Act: Find the item in the room
        Item foundItem = playerService.findItemInRoom(mockRoom, itemName);

        // Assert: The item is found
        assertNotNull(foundItem, "The item should be found in the room.");
        assertEquals(itemName, foundItem.getName(), "The item's name should match.");
    }

    /**
     * Tests that an item is not found in the room if it does not exist.
     */
    @Test
    void testFindItemInRoomItemDoesNotExist() {
        // Arrange: Mock an item in the room
        String itemName = "Salad";
        Item mockItem = mock(Item.class);
        when(mockItem.getName()).thenReturn("Test");
        when(mockRoom.getContent()).thenReturn(List.of(mockItem));

        // Act: Try to find an item that doesn't exist
        Item foundItem = playerService.findItemInRoom(mockRoom, itemName);

        // Assert: The item is not found
        assertNull(foundItem, "The item should not be found in the room.");
    }

    /**
     * Tests that updating the player's level sets the new level,
     * updates the player's position, and marks the current room as visited and revealed.
     */
    @Test
    void testUpdateLevel() {
        // Arrange: Mock a new level and room
        Level newLevel = mock(Level.class);
        Room newRoom = mock(Room.class);
        when(mockPlayer.getRow()).thenReturn(1);
        when(mockPlayer.getCol()).thenReturn(1);
        when(newLevel.getRoom(1, 1)).thenReturn(newRoom);

        // Act: Update the player's level
        playerService.updateLevel(newLevel);

        // Assert: Verify level and visibility updates
        verify(mockPlayer).setCurrentLevel(newLevel);
        verify(mockLevelService).revealAdjacentRooms(newLevel, 1, 1);
        verify(newRoom).setVisited(true);
        verify(newRoom).setRevealed(true);
    }
}
