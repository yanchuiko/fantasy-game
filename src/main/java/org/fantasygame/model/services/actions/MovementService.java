package org.fantasygame.model.services.actions;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.content.obstacles.Obstacle;
import org.fantasygame.model.entities.content.obstacles.traps.Trap;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.RoomService;
import org.fantasygame.model.services.LevelService;

import java.util.ArrayList;
import java.util.List;

public class MovementService {
    private final Player player;
    private final LevelService levelService;
    private final RoomService roomService;

    /**
     * Constructs a MovementService with the necessary dependencies.
     * @param player       the player to move
     * @param levelService handles level-related operations like visibility
     * @param roomService  manages room content (items, obstacles)
     */
    public MovementService(Player player, LevelService levelService, RoomService roomService) {
        this.player = player;
        this.levelService = levelService;
        this.roomService = roomService;
    }

    /**
     * Attempts to move the player in the specified direction.
     * <p>If the move is valid, updates the player's position, reveals the new room,
     * and handles any traps, obstacles, or tools in the room.</p>
     *
     * @param direction one of "forward", "back", "left", "right"
     * @return feedback message describing the outcome of the move
     */
    public String move(String direction) {
        // Wake the player if they were asleep and missed a turn
        if (player.isAsleep()) {
            player.setAsleep(false);
            return "You were asleep and missed your turn!";
        }

        // Determine the new position based on the direction
        int[] newPosition = calculateNewPosition(direction);
        int newRow = newPosition[0];
        int newCol = newPosition[1];

        // Get the current level's grid
        Room[][] grid = player.getCurrentLevel().getGrid();

        // Validate the move within level boundaries
        if (!isValidPosition(newRow, newCol, grid)) {
            ErrorHandler.throwError(500);
        }

        // Get the room the player is moving into
        Room newRoom = grid[newRow][newCol];

        // Prevent movement into a wall
        if ("wall".equals(newRoom.getType())) {
            ErrorHandler.throwError(501);
        }

        // Update player's location
        player.setRow(newRow);
        player.setCol(newCol);

        // Mark the new room as discovered
        newRoom.setVisited(true);
        newRoom.setRevealed(true);

        // Handle tools (Hammer) and obstacles (Traps, Scientists) in the room
        String toolsFeedback = activateTool(newRoom);
        String obstacleFeedback = manageRoomObstacles(newRoom);

        // Update visibility around the player's new position
        levelService.handleRoomVisibility(player.getCurrentLevel(), player);

        // Combine feedback messages
        String combinedFeedback = "";
        if (!toolsFeedback.isEmpty()) {
            combinedFeedback += toolsFeedback;
        }
        if (!obstacleFeedback.isEmpty()) {
            if (!toolsFeedback.isEmpty()) {
                combinedFeedback += " ";
            }
            combinedFeedback += obstacleFeedback;
        }

        return combinedFeedback.trim();
    }

    /**
     * Determines the new coordinates based on the direction.
     * @param direction one of "forward", "back", "left", "right"
     * @return the new coordinates as an array [row, col]
     */
    private int[] calculateNewPosition(String direction) {
        return switch (direction.toLowerCase()) {
            case "forward" -> new int[]{player.getRow() - 1, player.getCol()};
            case "back" -> new int[]{player.getRow() + 1, player.getCol()};
            case "left" -> new int[]{player.getRow(), player.getCol() - 1};
            case "right" -> new int[]{player.getRow(), player.getCol() + 1};
            default -> null;
        };
    }

    /**
     * Checks if a target position is within the grid bounds.
     * @param row  the target row
     * @param col the target column
     * @param grid the current level's grid
     * @return true if the position is valid, false otherwise
     *
     */
    private boolean isValidPosition(int row, int col, Room[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    /**
     * Activates any tools the player might have that affect the current room,
     * such as neutralizing traps if the player has a Hammer.
     * @param room the current room after movement
     * @return feedback describing the interaction with tools
     */
    private String activateTool(Room room) {
        StringBuilder feedback = new StringBuilder();

        // If player has a Hammer, neutralize traps in the room
        if (player.getInventory().contains("Hammer")) {
            List<Object> traps = new ArrayList<>();

            // Find all traps
            for (Object content : room.getContent()) {
                if (content instanceof Trap trap) {
                    trap.setNeutralized(true);
                    traps.add(trap);
                }
            }

            // Remove all neutralized traps
            roomService.removeAllContent(room, traps);

            if (!traps.isEmpty()) {
                feedback.append("Your Hammer smashed a trap that was in this room!");
            }
        }

        // If player has an Alarm Clock and is asleep, wake them up
        if (player.getInventory().contains("Alarm Clock") && player.isAsleep()) {
            player.setAsleep(false);
            if (!feedback.isEmpty()) {
                feedback.append(" ");
            }
            feedback.append("Your Alarm Clock woke you up before you could fall asleep!");
        }

        return feedback.toString();
    }

    /**
     * Manages obstacles in the current room. If the player has a Freeze Spell, neutralize all obstacles.
     * Otherwise, trigger obstacles and remove them if they are neutralized.
     *
     * @param room the current room after movement
     * @return feedback describing the interaction with obstacles
     */
    private String manageRoomObstacles(Room room) {
        StringBuilder feedback = new StringBuilder();

        boolean hasObstacles = false;

        // Check if there are obstacles in the room
        for (Object content : room.getContent()) {
            if (content instanceof Obstacle) {
                hasObstacles = true;
                break;
            }
        }

        // If obstacles are present and player has a Freeze Spell, neutralize them
        boolean hasFreezeSpell = player.getInventory().contains("Freeze Spell");
        if (hasObstacles && hasFreezeSpell) {
            Item item = player.getInventory().getItem("Freeze Spell");

            if (item != null && "Freeze Spell".equals(item.getName())) {
                item.use(player);
                player.getInventory().removeItem("Freeze Spell");
            }

            feedback.append("You used a Freeze Spell and neutralized the obstacles in the room.");
        }

        // Handle individual obstacles
        List<Object> obstaclesToRemove = new ArrayList<>();
        for (Object content : room.getContent()) {
            if (content instanceof Obstacle obstacle) {

                // If the obstacle is already neutralized, mark it for removal
                if (obstacle.isNeutralized()) {
                    obstaclesToRemove.add(obstacle);
                    continue;
                }

                // Trigger the obstacle's effect and append feedback if any
                String result = obstacle.trigger(player);
                if (result != null && !result.isEmpty()) {
                    if (!feedback.isEmpty()) {
                        feedback.append(" ");
                    }
                    feedback.append(result);
                }

                // Remove if the obstacle became neutralized after triggering
                if (obstacle.isNeutralized()) {
                    obstaclesToRemove.add(obstacle);
                }
            }
        }

        // Remove all neutralized obstacles from the room
        roomService.removeAllContent(room, obstaclesToRemove);
        return feedback.toString();
    }
}
