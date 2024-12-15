package org.fantasygame.model.services;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * LevelService is responsible for managing levels in the game.
 * It handles the initialization of levels, transitions between levels,
 * and room visibility based on player actions.
 */
public class LevelService {

    // Constants for room type symbols used in the level map
    public static final String EMPTY_ROOM_SYMBOL = "-";
    public static final String ENTRANCE_SYMBOL = "E";
    public static final String WALL_SYMBOL = "W";
    public static final String EXIT_SYMBOL = "X";

    private final RoomService roomService;

    /**
     * Constructor to initialize the LevelService with the RoomService.
     *
     * @param roomService the service responsible for managing rooms
     */
    public LevelService(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Initializes a new level based on the level number and the provided map file.
     * This method reads the map data (from the CSV my case), creates rooms, and sets up the entrance and exit.
     *
     * @param levelNumber the level number (e.g., 1, 2, 3)
     * @param filePath    the file path of the level map
     * @return a new Level object representing the initialized level
     */
    public Level initializeLevel(int levelNumber, String filePath) {
        List<String> mapLines = readFile(filePath); // Read the level map from the file
        Room[][] grid = new Room[5][5]; // 5x5 grid for each level

        // Find the starting row of the level in the map file
        int startRow = -1;

        // Level header in the map file
        String levelHeader = "Level " + levelNumber;

        // Find the line where the level starts in the map file
        for (int i = 0; i < mapLines.size(); i++) {
            if (mapLines.get(i).equalsIgnoreCase(levelHeader)) {
                startRow = i + 1; // The level's data starts after the header
                break;
            }
        }

        // Initialize rooms for the level
        for (int row = 0; row < 5; row++) {
            // Split the line into cells based on the comma separator
            String[] cells = mapLines.get(startRow + row).split(",");

            // Initialize each room in the row
            for (int col = 0; col < cells.length; col++) {
                String cell = cells[col].trim();
                String roomType;

                // Assign room type based on the map symbols
                switch (cell) {
                    case WALL_SYMBOL -> roomType = "wall";
                    case ENTRANCE_SYMBOL -> roomType = "entrance";
                    case EXIT_SYMBOL -> roomType = "exit";
                    case EMPTY_ROOM_SYMBOL -> roomType = "empty";
                    default -> {
                        ErrorHandler.throwError(400);
                        return null;
                    }
                }

                // Initialize the room based on the type
                grid[row][col] = roomService.initializeRoom(row, col, roomType, "Level " + levelNumber);
            }
        }

        // Get the entrance and exit coordinates for the level
        int[] entrance = getLevelEntrance(levelNumber);
        int[] exit = getLevelExit(levelNumber);

        // Return the initialized level
        return new Level(levelNumber, grid, entrance, exit);
    }

    /**
     * Returns the entrance coordinates for the specified level.
     *
     * @param levelNumber the level number
     * @return an array containing the row and column of the entrance
     */
    private int[] getLevelEntrance(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> new int[]{4, 1};
            case 2 -> new int[]{4, 3};
            case 3 -> new int[]{4, 0};
            default -> {
                ErrorHandler.throwError(401);
                yield null;
            }
        };
    }

    /**
     * Returns the exit coordinates for the specified level.
     *
     * @param levelNumber the level number
     * @return an array containing the row and column of the exit
     */
    private int[] getLevelExit(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> new int[]{0, 3};
            case 2 -> new int[]{0, 0};
            case 3 -> new int[]{0, 0};
            default -> {
                ErrorHandler.throwError(401);
                yield null;
            }
        };
    }

    /**
     * Reads the level map from a file and returns the lines as a list of strings.
     *
     * @param fileName the file path of the level map
     * @return a list of strings representing the lines in the map file
     */
    public static List<String> readFile(String fileName) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("File Error");
            e.printStackTrace();
        }

        return lines;
    }

    /**
     * Reveals the adjacent rooms of a given room by setting their visibility based on the player's position.
     *
     * @param level the current level of the game
     * @param row   the row index of the player's current position
     * @param col   the column index of the player's current position
     */
    public void revealAdjacentRooms(Level level, int row, int col) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Directions: up, down, left, right

        // Loop through all four directions
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            Room room = level.getRoom(newRow, newCol);

            // If the adjacent room is a wall, exit, or entrance, reveal it
            if (room != null && ("wall".equals(room.getType()) || "exit".equals(room.getType()) || "entrance".equals(room.getType()))) {
                room.setRevealed(true);
            }
        }
    }

    /**
     * Handles the visibility of rooms based on the player's position and reveals adjacent rooms.
     *
     * @param level  the current level of the game
     * @param player the player whose position is used to determine room visibility
     */
    public void handleRoomVisibility(Level level, Player player) {
        revealAdjacentRooms(level, player.getRow(), player.getCol());

        // Reveal the entrance room
        Room entranceRoom = level.getRoom(level.getEntrance()[0], level.getEntrance()[1]);
        if (entranceRoom != null) {
            entranceRoom.setVisited(true);
            entranceRoom.setRevealed(true);
        }
    }

    /**
     * Transition the player to the next level by setting his position to the entrance of the next level.
     * It also reveals adjacent rooms and updates the player's visibility.
     *
     * @param nextLevel the next level to transition to
     * @param player    the player who is transitioning to the next level
     */
    public void transitionToNextLevel(Level nextLevel, Player player) {
        // Set the player's position to the entrance of the next level
        player.setRow(nextLevel.getEntrance()[0]);
        player.setCol(nextLevel.getEntrance()[1]);

        // Reveal the starting room of the next level
        Room startingRoom = nextLevel.getRoom(player.getRow(), player.getCol());
        if (startingRoom != null) {
            startingRoom.setVisited(true);
            startingRoom.setRevealed(true);
            revealAdjacentRooms(nextLevel, player.getRow(), player.getCol());
        }

        // Reveal the entrance room of the next level
        Room entranceRoom = nextLevel.getRoom(nextLevel.getEntrance()[0], nextLevel.getEntrance()[1]);
        if (entranceRoom != null) {
            entranceRoom.setVisited(true);
            entranceRoom.setRevealed(true);
        }
    }
}
