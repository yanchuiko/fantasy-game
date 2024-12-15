package org.fantasygame.model.services.actions;

import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.content.items.boxes.Box;
import org.fantasygame.model.entities.core.Inventory;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.PlayerService;
import org.fantasygame.model.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PickUpServiceTest {

    private PickUpService pickUpService;
    private Player mockPlayer;
    private PlayerService mockPlayerService;
    private RoomService mockRoomService;
    private Room mockRoom;
    private Inventory mockInventory;

    @BeforeEach
    void setUp() {
        // Arrange: Mock dependencies
        mockPlayer = mock(Player.class);
        mockPlayerService = mock(PlayerService.class);
        mockRoomService = mock(RoomService.class);
        mockRoom = mock(Room.class);
        mockInventory = mock(Inventory.class);

        // Arrange: Link inventory to player
        when(mockPlayer.getInventory()).thenReturn(mockInventory);

        // Arrange: Initialize PickUpService with mocked dependencies
        pickUpService = new PickUpService(mockPlayer, mockPlayerService, mockRoomService);
    }

    /**
     * Tests that picking up a valid item adds it to the inventory, removes it from the room,
     * and returns the correct feedback message.
     */
    @Test
    void testPickUpValidItem() {
        // Arrange: Prepare a valid item
        String item = "Cake";
        Item mockItem = mock(Item.class);

        // Arrange: Mock behavior
        when(mockItem.getName()).thenReturn(item);
        when(mockPlayerService.getPlayerCurrentRoom()).thenReturn(mockRoom);
        when(mockPlayerService.findItemInRoom(mockRoom, item)).thenReturn(mockItem);
        when(mockInventory.addItem(mockItem)).thenReturn(true);

        // Act: Attempt to pick up the item
        String result = pickUpService.pickUp(item);

        // Assert: Verify interactions and feedback
        verify(mockPlayerService, times(1)).getPlayerCurrentRoom();
        verify(mockPlayerService, times(1)).findItemInRoom(mockRoom, item);
        verify(mockInventory, times(1)).addItem(mockItem);
        verify(mockRoomService, times(1)).removeContent(mockRoom, mockItem);
        assertEquals("You picked up Cake.", result, "Feedback message should confirm item pickup.");
    }

    /**
     * Tests that attempting to pick up an item not found in the room throws an exception
     * and does not interact with the inventory or room content.
     */
    @Test
    void testPickUpItemNotFound() {
        // Arrange: Prepare an item not in the room
        String item = "Cake";

        // Arrange: Mock behavior
        when(mockPlayerService.getPlayerCurrentRoom()).thenReturn(mockRoom);
        when(mockPlayerService.findItemInRoom(mockRoom, item)).thenReturn(null);

        // Act: Attempt to pick up the item and catch the exception
        RuntimeException exception = null;
        try {
            pickUpService.pickUp(item);
        } catch (RuntimeException e) {
            exception = e;
        }

        // Assert: Verify exception is thrown
        assertNotNull(exception, "Should throw RuntimeException for missing item.");

        // Assert: Verify interactions
        verify(mockPlayerService, times(1)).findItemInRoom(mockRoom, item);
        verify(mockInventory, never()).addItem(any());
        verify(mockRoomService, never()).removeContent(any(), any());
    }

    /**
     * Tests that attempting to pick up a box item throws an exception
     * and does not interact with the inventory or room content.
     */
    @Test
    void testPickUpBoxItem() {
        // Arrange: Prepare a box item
        String item = "Treasure Box";
        Box mockBox = mock(Box.class);

        // Arrange: Mock behavior
        when(mockPlayerService.getPlayerCurrentRoom()).thenReturn(mockRoom);
        when(mockPlayerService.findItemInRoom(mockRoom, item)).thenReturn(mockBox);

        // Act: Attempt to pick up the box and catch the exception
        RuntimeException exception = null;
        try {
            pickUpService.pickUp(item);
        } catch (RuntimeException e) {
            exception = e;
        }

        // Assert: Verify exception is thrown
        assertNotNull(exception, "Should throw RuntimeException for box item.");

        // Assert: Verify interactions
        verify(mockPlayerService, times(1)).findItemInRoom(mockRoom, item);
        verify(mockInventory, never()).addItem(any());
        verify(mockRoomService, never()).removeContent(any(), any());
    }

    /**
     * Tests that attempting to pick up an item when the inventory is full throws an exception
     * and does not remove the item from the room.
     */
    @Test
    void testPickUpInventoryFull() {
        // Arrange: Prepare an item with a full inventory
        String item = "Potion";
        Item mockItem = mock(Item.class);

        // Arrange: Mock behavior
        when(mockPlayerService.getPlayerCurrentRoom()).thenReturn(mockRoom);
        when(mockPlayerService.findItemInRoom(mockRoom, item)).thenReturn(mockItem);
        when(mockInventory.addItem(mockItem)).thenReturn(false); // Simulate full inventory

        // Act: Attempt to pick up the item and catch the exception
        RuntimeException exception = null;
        try {
            pickUpService.pickUp(item);
        } catch (RuntimeException e) {
            exception = e;
        }

        // Assert: Verify exception is thrown
        assertNotNull(exception, "Should throw RuntimeException for full inventory.");

        // Assert: Verify interactions
        verify(mockPlayerService, times(1)).findItemInRoom(mockRoom, item);
        verify(mockInventory, times(1)).addItem(mockItem);
        verify(mockRoomService, never()).removeContent(any(), any());
    }
}
