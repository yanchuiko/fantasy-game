package org.fantasygame.model.services.actions;

import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LookAroundServiceTest {

    private LookAroundService lookAroundService;
    private PlayerService mockPlayerService;
    private Room mockRoom;

    @BeforeEach
    void setUp() {
        // Arrange: Mock PlayerService and Room
        mockPlayerService = mock(PlayerService.class);
        mockRoom = mock(Room.class);

        // Arrange: Initialize LookAroundService with mocked PlayerService
        lookAroundService = new LookAroundService(mockPlayerService);
    }

    /**
     * Tests that looking around in a valid room returns a description of all items in the room.
     */
    @Test
    void testLookAroundWithValidRoom() {
        // Arrange: Set up a room with content
        when(mockPlayerService.getPlayerCurrentRoom()).thenReturn(mockRoom);
        when(mockRoom.getContent()).thenReturn(Arrays.asList("Cake", "Hammer", "Teleportation Spell"));

        // Act: Call the lookAround method
        String result = lookAroundService.lookAround();

        // Assert: Verify interactions and result
        verify(mockPlayerService, times(1)).getPlayerCurrentRoom(); // Ensure the current room is fetched
        verify(mockRoom, atLeast(1)).getContent(); // Ensure content is fetched at least once
        assertEquals("Cake, Hammer, Teleportation Spell", result, "Room description should list all items.");
    }

    /**
     * Tests that looking around in an empty room throws an IllegalArgumentException with the correct message.
     */
    @Test
    void testLookAroundWithEmptyRoom() {
        // Arrange: Set up a room with no content
        when(mockPlayerService.getPlayerCurrentRoom()).thenReturn(mockRoom);
        when(mockRoom.getContent()).thenReturn(Collections.emptyList()); // No content in the room

        // Act: Call the lookAround method and catch the exception
        IllegalArgumentException exception = null;
        try {
            lookAroundService.lookAround();
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // Assert: Verify the exception was thrown
        assertNotNull(exception, "Should throw IllegalArgumentException when the room is empty.");
        assertEquals("The room is empty", exception.getMessage(), "Exception message should indicate the room is empty.");

        // Assert: Verify interactions
        verify(mockPlayerService, times(1)).getPlayerCurrentRoom(); // Ensure the current room is fetched
        verify(mockRoom, times(1)).getContent(); // Ensure content is fetched once
    }
}
