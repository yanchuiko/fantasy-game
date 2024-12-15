package org.fantasygame.presenter.engine;

/**
 * Runs the main game loop, repeatedly updating the game's state,
 * processing player input, and rendering the current status.
 */
public class GameFlowManager {
    private final InputHandler inputHandler;
    private final CommandHandler commandHandler;
    private final GamePresenter gamePresenter;
    private final GameStateHandler gameStateHandler;

    /**
     * @param inputHandler       handles player input retrieval
     * @param commandHandler   interprets and executes player commands
     * @param gamePresenter           displays game status and messages
     * @param gameStateHandler   checks game conditions and handles level transitions
     */
    public GameFlowManager(InputHandler inputHandler, CommandHandler commandHandler, GamePresenter gamePresenter, GameStateHandler gameStateHandler) {
        this.inputHandler = inputHandler;
        this.commandHandler = commandHandler;
        this.gamePresenter = gamePresenter;
        this.gameStateHandler = gameStateHandler;
    }

    /**
     * Starts the main loop, continuing until the game ends.
     * Each iteration:
     * <ul>
     *   <li>Renders current game status</li>
     *   <li>Gets and processes player input</li>
     *   <li>Checks for level transitions and game over conditions</li>
     *   <li>Displays feedback and updates the view</li>
     * </ul>
     */
    public void start() {
        gamePresenter.clearScreen();

        while (true) {
            // Show current state of the game
            gamePresenter.renderGameStatus();

            // Get player's command and process it
            String userInput = inputHandler.getInput();
            String feedbackMessage = commandHandler.process(userInput);

            // Show feedback message before checking game over or level transition
            gamePresenter.showMessage(feedbackMessage);

            // Check if the player reached an exit and handle level transition if needed
            if (gameStateHandler.checkLevelTransition()) {
                // Update the current level and player position
                gameStateHandler.handleLevelTransition();

                // Check if the game has been won
                if (gameStateHandler.isGameWon()) {
                    gamePresenter.updateGameWon();
                    gamePresenter.clearScreen();
                    gamePresenter.renderGameStatus();
                    break;
                } else {
                    // Update the renderer with the new level
                    gamePresenter.updateCurrentLevel(gameStateHandler.getCurrentLevel());
                }
            }

            // Check if the game is over
            if (gameStateHandler.isGameOver()) {
                gamePresenter.updateGameOver();
                gamePresenter.clearScreen();
                gamePresenter.renderGameStatus();
                break;
            }

            gamePresenter.clearScreen();
        }
    }
}
