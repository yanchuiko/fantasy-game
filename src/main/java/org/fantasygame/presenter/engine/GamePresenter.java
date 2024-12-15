package org.fantasygame.presenter.engine;

import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.PlayerService;
import org.fantasygame.view.GameView;

/**
 * GamePresenter is responsible for preparing and presenting game status information to the player.
 * It gathers relevant data from the Player, PlayerService, and the current Level, then uses the GameView to display it.
 */
public class GamePresenter {

    private final GameView gameView;
    private final Player player;
    private final PlayerService playerService;
    private Level currentLevel;

    /**
     * Constructs a new GamePresenter object.
     *
     * @param gameView      the GameView used for displaying information
     * @param player        the Player instance representing the current player
     * @param currentLevel  the current Level the player is on
     * @param playerService the PlayerService used for retrieving player-related information
     */
    public GamePresenter(GameView gameView, Player player, Level currentLevel, PlayerService playerService) {
        this.gameView = gameView;
        this.player = player;
        this.currentLevel = currentLevel;
        this.playerService = playerService;
    }

    /**
     * Renders the current game status, including:
     * - The player's current level and room position.
     * - A description of the current room and its surroundings.
     * - The game's map, showing visited/unvisited rooms and walls/exits.
     * - The player's current power points and inventory.
     * This information is displayed via GameView.
     */
    public void renderGameStatus() {
        // Format the player's position
        String playerPosition = "Level " + currentLevel.getLevelNumber() +
                ", Room (" + (player.getRow() + 1) + ", " + (player.getCol() + 1) + ")";

        // Get a description of the current room
        String roomDescription = playerService.getRoomDescription();

        // Render a visual map of the current level
        String map = renderMapForView();

        // Retrieve the player's current power points (ensuring it is not negative)
        int powerPoints = Math.max(player.getPowerPoints(), 0);

        // Get a formatted inventory list of the player's items
        String inventory = player.getInventory().getFormattedInventory();

        // Display all gathered information to the player through the GameView
        gameView.displayGameStatus(playerPosition, roomDescription, map, powerPoints, inventory);
    }

    /**
     * Renders a map visualization for the current level, showing the player's position as 'P',
     * and representing walls, exits, entrances, and unexplored rooms.
     *
     * @return a string representing the rendered map
     */
    private String renderMapForView() {
        // Get the grid of rooms for the current level
        Room[][] grid = currentLevel.getGrid();

        // Create a 2D array of strings to represent each cell in the map
        String[][] mapGrid = new String[grid.length][grid[0].length];

        // Iterate over each room in the level's grid
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Room room = grid[row][col];

                // If the room is null or not revealed, display '?'
                // Otherwise, display a character based on the room type
                mapGrid[row][col] = (room == null || !room.isRevealed()) ? "?" :
                        switch (room.getType()) {
                            case "wall" -> "W";
                            case "exit" -> "X";
                            case "entrance" -> "E";
                            default -> ".";
                        };
            }
        }

        // Render the map with the player's current position highlighted as 'P'
        return gameView.renderMap(mapGrid, player.getRow(), player.getCol());
    }

    /**
     * Updates the current level reference after a transition or level change,
     * ensuring that subsequent renders reflect the newly entered level.
     *
     * @param newLevel the new Level to be set as the current level
     */
    public void updateCurrentLevel(Level newLevel) {
        this.currentLevel = newLevel;
    }

    /**
     * Displays a given message to the player.
     *
     * @param message the message to be displayed
     */
    public void showMessage(String message) {
        gameView.updateMessage(message);
    }

    /**
     * Updates the game view to display a message indicating that the player has won the game.
     */
    public void updateGameWon() {
        gameView.updateMessageGameWon();
    }

    /**
     * Updates the game view to display a message indicating that the game is over.
     */
    public void updateGameOver() {
        gameView.updateMessageGameOver();
    }

    /**
     * Updates the game view to display a message indicating that the player has found the exit.
     */
    public void updateFoundExit() {
        gameView.updateMessageFoundExit();
    }

    /**
     * Clears the screen, removing previously displayed content.
     * Useful for providing a fresh view before showing updated game status or messages.
     */
    public void clearScreen() {
        gameView.clearScreen();
    }
}
