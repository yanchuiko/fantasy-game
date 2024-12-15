package org.fantasygame.model.entities.items.tools;

import org.fantasygame.model.entities.content.items.tools.Hammer;
import org.fantasygame.model.entities.content.obstacles.traps.Trap;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class HammerTest {

    private Hammer hammer;
    private Player player;
    private Level mockLevel;
    private Room mockRoom;
    private List<Object> roomContents;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize Hammer, Player, Level, and Room
        hammer = new Hammer();
        player = mock(Player.class);
        mockLevel = mock(Level.class);
        mockRoom = mock(Room.class);

        // Arrange: Create room contents with traps and other items
        roomContents = new ArrayList<>();
        Trap trap1 = mock(Trap.class);
        Trap trap2 = mock(Trap.class);
        roomContents.add(trap1);
        roomContents.add("Cake");
        roomContents.add(trap2);

        // Arrange: Set up mocks
        when(player.getCurrentLevel()).thenReturn(mockLevel);
        when(player.getRow()).thenReturn(1);
        when(player.getCol()).thenReturn(1);
        when(mockLevel.getRoom(1, 1)).thenReturn(mockRoom);
        when(mockRoom.getContent()).thenReturn(roomContents);
    }

    /**
     * Tests that using the Hammer removes all traps from the room contents.
     */
    @Test
    void testUseRemovesTraps() {
        // Act: Use the Hammer
        hammer.use(player);

        // Assert: Verify the room contents no longer contain traps
        boolean containsTrap = false;
        for (Object item : roomContents) {
            if (item instanceof Trap) {
                containsTrap = true;
                break;
            }
        }

        Assertions.assertFalse(containsTrap, "Room should not contain traps after using the Hammer");
    }

    /**
     * Tests that using the Hammer on a room without traps leaves the room contents unchanged.
     */
    @Test
    void testUseWithNoTraps() {
        // Arrange: Remove traps from room contents
        roomContents.clear();
        roomContents.add("Cake");

        // Act: Use the Hammer
        hammer.use(player);

        // Assert: Verify the room contents remain unchanged
        Assertions.assertEquals(1, roomContents.size(), "Room contents size should remain the same if no traps are present");
        Assertions.assertEquals("Cake", roomContents.get(0), "The only item in the room should be 'Cake'");
    }
}
