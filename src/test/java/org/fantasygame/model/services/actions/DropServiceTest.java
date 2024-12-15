package org.fantasygame.model.services.actions;

import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.core.Inventory;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.PlayerService;
import org.fantasygame.model.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DropServiceTest {

    private DropService dropService;
    private Player mockPlayer;
    private PlayerService mockPlayerService;
    private RoomService mockRoomService;
    private Inventory mockInventory;
    private Room mockRoom;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize mocks
        mockPlayer = mock(Player.class);
        mockPlayerService = mock(PlayerService.class);
        mockRoomService = mock(RoomService.class);
        mockInventory = mock(Inventory.class);
        mockRoom = mock(Room.class);

        // Arrange: Link the mock inventory to the player
        when(mockPlayer.getInventory()).thenReturn(mockInventory);

        // Arrange: Mock the player's current room
        when(mockPlayerService.getPlayerCurrentRoom()).thenReturn(mockRoom);

        // Arrange: Initialize DropService with mocked dependencies
        dropService = new DropService(mockPlayer, mockPlayerService, mockRoomService);
    }

    /**
     * Tests that dropping a valid item removes it from the player's inventory,
     * adds it to the room, and returns the correct message.
     */
    @Test
    void testDropValidItem() {
        // Arrange: Prepare a valid item
        String item = "Cake";
        Item mockItem = mock(Item.class);

        // Arrange: Mock inventory and item behavior
        when(mockInventory.contains(item)).thenReturn(true);
        when(mockInventory.removeItem(item)).thenReturn(mockItem);
        when(mockItem.getName()).thenReturn(item);

        // Act: Attempt to drop the item
        String result = dropService.drop(item);

        // Assert: Verify the inventory is checked for the item
        verify(mockInventory, times(1)).contains(item);

        // Assert: Verify the item is removed from the inventory
        verify(mockInventory, times(1)).removeItem(item);

        // Assert: Verify the item is added to the room
        verify(mockRoomService, times(1)).addContent(mockRoom, mockItem);

        // Assert: Verify the feedback message matches the item dropped
        assertEquals("You dropped Cake.", result, "Feedback message should match the item dropped.");
    }

    /**
     * Tests that attempting to drop an item not in the inventory throws an exception
     * and does not interact with the room or inventory further.
     */
    @Test
    void testDropInvalidItem() {
        // Arrange: Prepare an invalid item
        String invalidItem = "Test";

        // Arrange: Mock inventory behavior for nonexistent item
        when(mockInventory.contains(invalidItem)).thenReturn(false);

        // Act: Attempt to drop the item and handle exception
        RuntimeException exception = null;
        try {
            dropService.drop(invalidItem);
        } catch (RuntimeException e) {
            exception = e;
        }

        // Assert: Verify the exception is thrown
        assertNotNull(exception, "Should throw an error for attempting to drop a nonexistent item.");
        assertNotNull(exception.getMessage(), "Error message should be provided.");

        // Assert: Verify no further interactions with inventory or room service
        verify(mockInventory, times(1)).contains(invalidItem);
        verify(mockInventory, never()).removeItem(invalidItem);
        verify(mockRoomService, never()).addContent(any(Room.class), any(Item.class));
    }
}
