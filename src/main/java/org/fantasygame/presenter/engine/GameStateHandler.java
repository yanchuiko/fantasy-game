package org.fantasygame.presenter.engine;

import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.LevelService;
import org.fantasygame.model.services.PlayerService;

/**
 * The GameStateHandler is responsible for overseeing the high-level state of the game,
 * including determining when the game is over and handling level transitions.
 */
public class GameStateHandler {

    private final Player player;
    private Level currentLevel;

    private final PlayerService playerService;
    private final LevelService levelService;

    private final GamePresenter gamePresenter;

    /**
     * Constructs a new GameStateHandler.
     *
     * @param player        the Player whose state is being managed
     * @param currentLevel  the current Level the player is on
     * @param playerService the PlayerService for manipulating player data
     * @param levelService  the LevelService for handling level initialization and transitions
     * @param gamePresenter      the GamePresenter for updating the game view
     */
    public GameStateHandler(Player player, Level currentLevel, PlayerService playerService, LevelService levelService, GamePresenter gamePresenter) {
        this.player = player;
        this.currentLevel = currentLevel;
        this.playerService = playerService;
        this.levelService = levelService;
        this.gamePresenter = gamePresenter;
    }

    /**
     * Checks if the player has lost the game.
     *
     * @return true if the player has lost, false otherwise
     */
    public boolean isGameOver() {
        return player.getPowerPoints() <= 0;
    }

    /**
     * Checks if the player has won the game.
     * @return true if the player has won, false otherwise
     */
    public boolean isGameWon() {
        return currentLevel.getLevelNumber() == 3 &&
                player.getRow() == currentLevel.getExit()[0] &&
                player.getCol() == currentLevel.getExit()[1];
    }

    /**
     * Checks whether the player has reached an exit in the current level.
     *
     * @return true if the player is currently in an "exit" room, false otherwise
     */
    public boolean checkLevelTransition() {
        Room currentRoom = currentLevel.getRoom(player.getRow(), player.getCol());
        return "exit".equals(currentRoom.getType());
    }

    /**
     * Handles transitioning to the next level.
     */
    public void handleLevelTransition() {
        // If the player is at the end of the final level, the game is won
        if (currentLevel.getLevelNumber() == 3 &&
                player.getRow() == currentLevel.getExit()[0] &&
                player.getCol() == currentLevel.getExit()[1]) {
                gamePresenter.updateGameWon();
                return;
        }

        // Determine the next level number.
        int nextLevelNumber = currentLevel.getLevelNumber() + 1;

        // Initialize and transition to the next level
        this.currentLevel = levelService.initializeLevel(nextLevelNumber, "src/main/resources/map.csv");
        levelService.transitionToNextLevel(currentLevel, player);
        playerService.updateLevel(currentLevel);

        gamePresenter.updateFoundExit();
    }

    /**
     * Returns the current level.
     *
     * @return the current Level instance
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }
}
