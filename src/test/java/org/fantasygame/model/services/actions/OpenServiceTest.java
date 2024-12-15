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

class OpenServiceTest {

    private OpenService openService;
    private Player mockPlayer;
    private PlayerService mockPlayerService;
    private RoomService mockRoomService;
    private Inventory mockInventory;
    private Room mockRoom;

    @BeforeEach
    void setUp() {
        // Arrange: Mock dependencies
        mockPlayer = mock(Player.class);
        mockPlayerService = mock(PlayerService.class);
        mockRoomService = mock(RoomService.class);
        mockInventory = mock(Inventory.class);
        mockRoom = mock(Room.class);

        // Arrange: Link the mock inventory to the player
        when(mockPlayer.getInventory()).thenReturn(mockInventory);

        // Arrange: Mock the player's current room
        when(mockPlayerService.getPlayerCurrentRoom()).thenReturn(mockRoom);

        // Arrange: Initialize OpenService with mocked dependencies
        openService = new OpenService(mockPlayer, mockPlayerService, mockRoomService);
    }

    /**
     * Tests that opening a valid box with the correct tool invokes its use method,
     * removes the box from the room, and returns the expected message.
     */
    @Test
    void testOpenValidBox() {
        // Arrange: Prepare a valid box and tool
        String box = "Treasure Box";
        String tool = "Spanner";
        Box mockBox = mock(Box.class);

        // Arrange: Mock box behavior
        when(mockBox.use(mockPlayer)).thenReturn("You found a power points!");
        when(mockPlayerService.findItemInRoom(mockRoom, box)).thenReturn(mockBox);

        // Arrange: Mock inventory behavior
        when(mockInventory.contains(tool)).thenReturn(true);

        // Act: Attempt to open the box
        String result = openService.open(box);

        // Assert: Verify interactions and feedback
        verify(mockPlayerService, times(1)).findItemInRoom(mockRoom, box);
        verify(mockBox, times(1)).use(mockPlayer);
        verify(mockRoomService, times(1)).removeContent(mockRoom, mockBox);
        assertEquals("You found a power points!", result, "Feedback message should match the box's content.");
    }

    /**
     * Tests that attempting to open a non-box item throws an IllegalArgumentException
     * and does not interact with the room or inventory.
     */
    @Test
    void testOpenInvalidItem() {
        // Arrange: Prepare a non-box item
        String invalidItem = "Test";
        Item mockItem = mock(Item.class);

        // Arrange: Mock finding the invalid item in the room
        when(mockPlayerService.findItemInRoom(mockRoom, invalidItem)).thenReturn(mockItem);

        // Act: Attempt to open the item and catch the exception
        IllegalArgumentException exception = null;
        try {
            openService.open(invalidItem);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // Assert: Verify the exception is thrown
        assertNotNull(exception, "Should throw an IllegalArgumentException for attempting to open a non-box item.");
        assertEquals("There is no such box in this room", exception.getMessage());

        // Assert: Verify interactions
        verify(mockPlayerService, times(1)).findItemInRoom(mockRoom, invalidItem);
        verify(mockRoomService, never()).removeContent(any(Room.class), any(Item.class));
    }

    /**
     * Tests that attempting to open a box without the required tool throws an IllegalArgumentException
     * and does not interact with the room or the box.
     */
    @Test
    void testOpenBoxWithoutTool() {
        // Arrange: Prepare a box without the required tool
        String box = "Pandora Box";
        Box mockBox = mock(Box.class);

        // Arrange: Mock finding the box in the room
        when(mockPlayerService.findItemInRoom(mockRoom, box)).thenReturn(mockBox);

        // Arrange: Mock inventory behavior to indicate tool is missing
        when(mockInventory.contains("Spanner")).thenReturn(false);

        // Act: Attempt to open the box and catch the exception
        IllegalArgumentException exception = null;
        try {
            openService.open(box);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // Assert: Verify the exception is thrown
        assertNotNull(exception, "Should throw an IllegalArgumentException for attempting to open a box without a tool.");
        assertEquals("You need a Spanner to open this box", exception.getMessage());

        // Assert: Verify interactions
        verify(mockPlayerService, times(1)).findItemInRoom(mockRoom, box);
        verify(mockRoomService, never()).removeContent(any(Room.class), any(Item.class));
    }
}
