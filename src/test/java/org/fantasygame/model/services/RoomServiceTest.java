package org.fantasygame.model.services;

import org.fantasygame.model.entities.content.ContentFactory;
import org.fantasygame.model.entities.content.obstacles.Obstacle;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class RoomServiceTest {

    /**
     * Tests that a normal room is initialized with generated content
     * and all its properties are correctly set.
     */
    @Test
    void testInitializeRoomWithNormalType() {
        // Arrange: Mock ContentFactory and generate room content
        ContentFactory mockFactory = mock(ContentFactory.class);
        List<Object> mockContent = Arrays.asList(new Object(), new Object());
        when(mockFactory.generateRoomContent("Level 1")).thenReturn(mockContent);

        RoomService roomService = new RoomService(mockFactory);

        // Act: Initialize a "normal" room
        Room room = roomService.initializeRoom(0, 0, "normal", "Level 1");

        // Assert: Verify content generation and room properties
        verify(mockFactory).generateRoomContent("Level 1");
        assert room != null : "Room should not be null";
        assert room.getRow() == 0 : "Row should be 0";
        assert room.getCol() == 0 : "Column should be 0";
        assert room.getType().equals("normal") : "Type should be 'normal'";
        assert room.getContent().equals(mockContent) : "Room content should match generated content";
    }

    /**
     * Tests that a wall room is initialized with empty content and content generation is skipped.
     */
    @Test
    void testInitializeRoomWithWallType() {
        // Arrange: Mock ContentFactory
        ContentFactory mockFactory = mock(ContentFactory.class);
        RoomService roomService = new RoomService(mockFactory);

        // Act: Initialize a "wall" room
        Room room = roomService.initializeRoom(1, 1, "wall", "Level 1");

        // Assert: Verify content generation is skipped and room properties
        verify(mockFactory, never()).generateRoomContent(anyString());
        assert room != null : "Room should not be null";
        assert room.getRow() == 1 : "Row should be 1";
        assert room.getCol() == 1 : "Column should be 1";
        assert room.getType().equals("wall") : "Type should be 'wall'";
        assert room.getContent().isEmpty() : "Room content should be empty for wall type";
    }

    /**
     * Tests that an item is added to a room's content.
     */
    @Test
    void testAddContent() {
        // Arrange: Create a room and an item
        ContentFactory mockFactory = mock(ContentFactory.class);
        RoomService roomService = new RoomService(mockFactory);

        Room room = new Room(0, 0, "normal");
        Object item = new Object();

        // Act: Add the item to the room
        roomService.addContent(room, item);

        // Assert: Verify the item is in the room
        assert room.getContent().contains(item) : "Room content should contain added item";
    }

    /**
     * Tests that an item is removed from a room's content.
     */
    @Test
    void testRemoveContent() {
        // Arrange: Create a room and add items
        ContentFactory mockFactory = mock(ContentFactory.class);
        RoomService roomService = new RoomService(mockFactory);

        Room room = new Room(0, 0, "normal");
        Object item1 = new Object();
        Object item2 = new Object();
        room.getContent().addAll(Arrays.asList(item1, item2));

        // Act: Remove one item
        roomService.removeContent(room, item1);

        // Assert: Verify item removal and other items remain
        assert !room.getContent().contains(item1) : "Room content should not contain removed item";
        assert room.getContent().contains(item2) : "Room content should still contain other items";
    }

    /**
     * Tests that all specified items are added to a room's content.
     */
    @Test
    void testAddAllContent() {
        // Arrange: Create a room and items
        ContentFactory mockFactory = mock(ContentFactory.class);
        RoomService roomService = new RoomService(mockFactory);

        Room room = new Room(0, 0, "normal");
        List<Object> items = Arrays.asList(new Object(), new Object());

        // Act: Add all items to the room
        roomService.addAllContent(room, items);

        // Assert: Verify all items are in the room
        assert room.getContent().containsAll(items) : "Room content should contain all added items";
    }

    /**
     * Tests that all specified items are removed from a room's content.
     */
    @Test
    void testRemoveAllContent() {
        // Arrange: Create a room with multiple items
        ContentFactory mockFactory = mock(ContentFactory.class);
        RoomService roomService = new RoomService(mockFactory);

        Room room = new Room(0, 0, "normal");
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        room.getContent().addAll(Arrays.asList(item1, item2, item3));

        List<Object> itemsToRemove = Arrays.asList(item1, item3);

        // Act: Remove specified items
        roomService.removeAllContent(room, itemsToRemove);

        // Assert: Verify specified items are removed, others remain
        assert !room.getContent().contains(item1) : "Room content should not contain removed item1";
        assert !room.getContent().contains(item3) : "Room content should not contain removed item3";
        assert room.getContent().contains(item2) : "Room content should still contain item2";
    }

    /**
     * Tests that all neutralized obstacles are removed from a room's content,
     * while other items and non-neutralized obstacles remain.
     */
    @Test
    void testRemoveAllObstacles() {
        // Arrange: Mock dependencies and setup room content
        ContentFactory mockFactory = mock(ContentFactory.class);
        RoomService roomService = new RoomService(mockFactory);

        Player mockPlayer = mock(Player.class);
        Level mockLevel = mock(Level.class);
        Room mockRoom = mock(Room.class);
        when(mockPlayer.getCurrentLevel()).thenReturn(mockLevel);
        when(mockLevel.getRoom(0, 0)).thenReturn(mockRoom);

        Obstacle obstacle1 = mock(Obstacle.class);
        Obstacle obstacle2 = mock(Obstacle.class);
        Object item = new Object();
        when(obstacle1.isNeutralized()).thenReturn(true);  // Neutralized
        when(obstacle2.isNeutralized()).thenReturn(false); // Not neutralized

        List<Object> roomContent = new ArrayList<>(Arrays.asList(obstacle1, obstacle2, item));
        when(mockRoom.getContent()).thenReturn(roomContent);

        // Act: Remove all neutralized obstacles
        roomService.removeAllObstacles(mockPlayer);

        // Assert: Verify only neutralized obstacles are removed
        assert !roomContent.contains(obstacle1) : "Neutralized obstacle should be removed";
        assert roomContent.contains(obstacle2) : "Non-neutralized obstacle should remain";
        assert roomContent.contains(item) : "Non-obstacle items should remain";
    }
}
