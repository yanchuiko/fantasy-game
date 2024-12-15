package org.fantasygame.model.entities.items.spells;

import org.fantasygame.model.entities.content.items.spells.FreezeSpell;
import org.fantasygame.model.entities.content.obstacles.Obstacle;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class FreezeSpellTest {

    private FreezeSpell freezeSpell;
    private Player mockPlayer;
    private Level mockLevel;
    private Room mockRoom;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize FreezeSpell and mocks
        freezeSpell = new FreezeSpell();
        mockPlayer = mock(Player.class);
        mockLevel = mock(Level.class);
        mockRoom = mock(Room.class);

        // Arrange: Set up mock player's current position and level
        when(mockPlayer.getCurrentLevel()).thenReturn(mockLevel);
        when(mockPlayer.getRow()).thenReturn(2);
        when(mockPlayer.getCol()).thenReturn(3);

        // Arrange: Set up mock level to return a specific room
        when(mockLevel.getRoom(2, 3)).thenReturn(mockRoom);
    }

    /**
     * Tests that the FreezeSpell neutralizes all obstacles in the current room
     * and returns null after use.
     */
    @Test
    void testNeutralizesObstaclesInRoom() {
        // Arrange: Prepare mock obstacles in the room
        Obstacle obstacle1 = mock(Obstacle.class);
        Obstacle obstacle2 = mock(Obstacle.class);
        List<Object> roomContent = new ArrayList<>();
        roomContent.add(obstacle1);
        roomContent.add(obstacle2);
        when(mockRoom.getContent()).thenReturn(roomContent);

        // Act: Use the FreezeSpell on the player
        String result = freezeSpell.use(mockPlayer);

        // Assert: Verify that all obstacles in the room are neutralized
        verify(obstacle1).setNeutralized(true);
        verify(obstacle2).setNeutralized(true);

        // Assert: Verify the FreezeSpell returns null after use
        assertNull(result, "FreezeSpell should return null after use");
    }

    /**
     * Tests that the FreezeSpell does nothing when there are no obstacles in the current room
     * and returns null.
     */
    @Test
    void testDoesNothingWhenNoObstaclesInRoom() {
        // Arrange: Set up room content with no obstacles
        List<Object> roomContent = new ArrayList<>();
        when(mockRoom.getContent()).thenReturn(roomContent);

        // Act: Use the FreezeSpell on the player
        String result = freezeSpell.use(mockPlayer);

        // Assert: Verify that getContent is called but no obstacles are interacted with
        verify(mockRoom).getContent();

        // Assert: Verify the FreezeSpell returns null when no obstacles are present
        assertNull(result, "FreezeSpell should return null when there are no obstacles");
    }
}
