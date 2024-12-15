package org.fantasygame.model.entities.items.tools;

import org.fantasygame.model.entities.content.items.boxes.Box;
import org.fantasygame.model.entities.content.items.tools.Spanner;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class SpannerTest {

    private Spanner spanner;
    private Player player;
    private Level mockLevel;
    private Room mockRoom;
    private List<Object> roomContents;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize Spanner, Player, Level, and Room
        spanner = new Spanner();
        player = mock(Player.class);
        mockLevel = mock(Level.class);
        mockRoom = mock(Room.class);

        // Arrange: Mock room contents with boxes and other items
        roomContents = new ArrayList<>();
        Box mockBox1 = mock(Box.class);
        Box mockBox2 = mock(Box.class);
        roomContents.add(mockBox1);
        roomContents.add("Cake");
        roomContents.add(mockBox2);

        // Arrange: Set up Player's current room and level
        when(player.getCurrentLevel()).thenReturn(mockLevel);
        when(player.getRow()).thenReturn(1);
        when(player.getCol()).thenReturn(1);
        when(mockLevel.getRoom(1, 1)).thenReturn(mockRoom);
        when(mockRoom.getContent()).thenReturn(roomContents);
    }

    /**
     * Tests that using the Spanner opens all boxes in the current room.
     */
    @Test
    void testUseOpensBoxes() {
        // Act: Use the Spanner
        spanner.use(player);

        // Assert: Verify all boxes in the room have their `use` method called
        for (Object content : roomContents) {
            if (content instanceof Box) {
                verify((Box) content).use(player);
            }
        }
    }

    /**
     * Tests that using the Spanner in a room with no boxes does nothing.
     */
    @Test
    void testUseWithNoBoxes() {
        // Arrange: Remove all boxes from the room
        roomContents.clear();
        roomContents.add("Cake");

        // Act: Use the Spanner
        spanner.use(player);

        // Assert: Verify no `use` method is called on non-box items
        for (Object content : roomContents) {
            if (content instanceof Box) {
                verify((Box) content, never()).use(player);
            }
        }
    }
}
