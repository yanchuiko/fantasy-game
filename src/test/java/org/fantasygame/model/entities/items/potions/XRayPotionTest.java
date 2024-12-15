package org.fantasygame.model.entities.items.potions;

import org.fantasygame.model.entities.content.items.potions.XRayPotion;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class XRayPotionTest {

    private XRayPotion xRayPotion;
    private Player player;
    private Level mockLevel;
    private Room[][] roomGrid;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize XRayPotion and Player
        xRayPotion = new XRayPotion();
        player = new Player(0, 0, 100);

        // Arrange: Mock Level and set up a 3x3 room grid
        mockLevel = mock(Level.class);
        roomGrid = new Room[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String type = (i == 1 && j == 1) ? "wall" : "normal";
                roomGrid[i][j] = new Room(i, j, type);
            }
        }

        // Arrange: Mock Level's grid and room retrieval methods
        when(mockLevel.getGrid()).thenReturn(roomGrid);
        for (int i = 0; i < roomGrid.length; i++) {
            for (int j = 0; j < roomGrid[i].length; j++) {
                when(mockLevel.getRoom(i, j)).thenReturn(roomGrid[i][j]);
            }
        }

        // Arrange: Set the player's current level
        player.setCurrentLevel(mockLevel);
    }

    /**
     * Tests that using the X-Ray Potion reveals at least one normal room
     * and provides the appropriate message.
     */
    @Test
    void testUseRevealsRoom() {
        // Act: Use the X-Ray Potion
        String result = xRayPotion.use(player);

        // Assert: Verify that a normal room was revealed
        boolean roomRevealed = false;
        for (Room[] row : roomGrid) {
            for (Room room : row) {
                if (room.isRevealed()) {
                    roomRevealed = true;
                    Assertions.assertEquals("normal", room.getType(), "Revealed room should be 'normal'");
                    break;
                }
            }
        }
        Assertions.assertTrue(roomRevealed, "At least one room should be revealed");
        Assertions.assertTrue(result.contains("You drank the X-Ray Potion and revealed a hidden room!"),
                "Result should indicate a room was revealed");
    }

    /**
     * Tests that using the X-Ray Potion on a room with content provides a
     * message listing the content.
     */
    @Test
    void testUseRevealedRoomMessage() {
        // Arrange: Add content to a specific room
        Room targetRoom = roomGrid[2][2];
        targetRoom.getContent().add("Cake");
        targetRoom.getContent().add("Spanner");

        // Arrange: Mock the Level to always return this room
        when(mockLevel.getRoom(anyInt(), anyInt())).thenReturn(targetRoom);

        // Act: Use the X-Ray Potion
        String result = xRayPotion.use(player);

        // Assert: Verify the room was revealed and content is included in the message
        Assertions.assertTrue(targetRoom.isRevealed(), "Room (2, 2) should be revealed");
        Assertions.assertTrue(result.contains("Contains: Cake, Spanner"),
                "Message should include room content");
    }

    /**
     * Tests that using the X-Ray Potion does not reveal special rooms
     * (wall, entrance, exit).
     */
    @Test
    void testUseDoesNotRevealSpecialRooms() {
        // Arrange: Set specific room types
        Room wallRoom = roomGrid[1][1];
        wallRoom.setType("wall");

        Room entranceRoom = roomGrid[0][0];
        entranceRoom.setType("entrance");

        Room exitRoom = roomGrid[2][2];
        exitRoom.setType("exit");

        // Act: Use the X-Ray Potion multiple times
        for (int i = 0; i < 10; i++) {
            xRayPotion.use(player);
        }

        // Assert: Verify special rooms are not revealed
        Assertions.assertFalse(wallRoom.isRevealed(), "Wall rooms should not be revealed");
        Assertions.assertFalse(entranceRoom.isRevealed(), "Entrance rooms should not be revealed");
        Assertions.assertFalse(exitRoom.isRevealed(), "Exit rooms should not be revealed");

        // Assert: Verify normal rooms are still revealed
        boolean normalRoomRevealed = false;
        for (Room[] row : roomGrid) {
            for (Room room : row) {
                if (!room.getType().equals("wall") &&
                        !room.getType().equals("entrance") &&
                        !room.getType().equals("exit") &&
                        room.isRevealed()) {
                    normalRoomRevealed = true;
                    break;
                }
            }
        }
        Assertions.assertTrue(normalRoomRevealed, "At least one normal room should be revealed");
    }
}
