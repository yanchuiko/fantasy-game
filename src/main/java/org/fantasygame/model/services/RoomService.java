package org.fantasygame.model.services;

import org.fantasygame.model.entities.content.ContentFactory;
import org.fantasygame.model.entities.content.obstacles.Obstacle;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * RoomService is responsible for managing rooms in the game.
 * Handles the creation, modification, and removal of room content,
 * such as items and obstacles, within a given room.
 */
public class RoomService {

    private final ContentFactory contentFactory;

    /**
     * Constructor that initializes the RoomService with a given ContentFactory.
     *
     * @param contentFactory the factory responsible for generating room content
     */
    public RoomService(ContentFactory contentFactory) {
        this.contentFactory = contentFactory;
    }

    /**
     * Initializes a new room at the specified position with the given type and level.
     * Adds content to the room if it's not a wall or exit or entrance.
     *
     * @param row   the row index of the room
     * @param col   the column index of the room
     * @param type  the type of the room (e.g., "normal", "wall", "exit", "entrance")
     * @param level the level in which the room exists (used to generate content)
     * @return the newly initialized room
     */
    public Room initializeRoom(int row, int col, String type, String level) {
        Room room = new Room(row, col, type);

        // Only generate content for rooms that are not walls or exits or entrances
        if (!type.equals("wall") && !type.equals("exit") && !type.equals("entrance")) {
            List<Object> content = contentFactory.generateRoomContent(level);
            addAllContent(room, content);
        }

        return room;
    }

    /**
     * Adds a single item to the content of the specified room.
     *
     * @param room the room where the item will be added
     * @param item the item to be added to the room's content
     */
    public void addContent(Room room, Object item) {
        room.getContent().add(item);
    }

    /**
     * Removes a single item from the content of the specified room.
     *
     * @param room the room where the item will be removed from
     * @param item the item to be removed from the room's content
     */
    public void removeContent(Room room, Object item) {
        room.getContent().remove(item);
    }

    /**
     * Adds a list of items to the content of the specified room.
     *
     * @param room  the room where the items will be added
     * @param items the list of items to be added to the room's content
     */
    public void addAllContent(Room room, List<Object> items) {
        room.getContent().addAll(items);
    }

    /**
     * Removes a list of items from the content of the specified room.
     *
     * @param room  the room where the items will be removed from
     * @param items the list of items to be removed from the room's content
     */
    public void removeAllContent(Room room, List<Object> items) {
        room.getContent().removeAll(items);
    }

    /**
     * Removes all neutralized obstacles from the current room of the given player.
     * The obstacles are identified and removed if they are neutralized.
     *
     * @param player the player whose current room will be processed
     */
    public void removeAllObstacles(Player player) {
        Room currentRoom = player.getCurrentLevel().getRoom(player.getRow(), player.getCol());
        List<Object> obstaclesToRemove = new ArrayList<>();

        // Loop through all the content in the room
        for (Object content : currentRoom.getContent()) {
            // Check if the content is an obstacle and if it has been neutralized
            if (content instanceof Obstacle obstacle && obstacle.isNeutralized()) {
                obstaclesToRemove.add(obstacle); // Add neutralized obstacles to the removal list
            }
        }

        // Remove the identified neutralized obstacles from the room's content
        removeAllContent(currentRoom, obstaclesToRemove);
    }
}
