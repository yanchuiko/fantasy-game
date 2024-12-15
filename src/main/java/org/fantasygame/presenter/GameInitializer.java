package org.fantasygame.presenter;

import org.fantasygame.presenter.engine.*;
import org.fantasygame.model.entities.content.ContentFactory;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.services.LevelService;
import org.fantasygame.model.services.PlayerService;
import org.fantasygame.model.services.RoomService;
import org.fantasygame.view.GameView;

/**
 * Sets up and starts the game, initializing all core components
 * and launching the main game loop.
 */
public class GameInitializer {

    private static GameInitializer instance;

    private final GameView gameView;
    private final PlayerService playerService;
    private final LevelService levelService;
    private final Level currentLevel;
    private final Player player;
    private final GamePresenter gamePresenter;

    /**
     * Initializes the game environment, including the view, services,
     * initial level, and player.
     */
    private GameInitializer() {
        this.gameView = new GameView();

        // Create a RoomService with a ContentFactory to populate rooms with items and obstacles
        RoomService roomService = new RoomService(new ContentFactory());

        // Set up level handling
        this.levelService = new LevelService(roomService);

        // Initialize the first level and the player starting position
        this.currentLevel = levelService.initializeLevel(1, "src/main/resources/map.csv");
        this.player = new Player(4, 1, 100);

        // PlayerService handles actions like moving, picking up items, etc
        this.playerService = new PlayerService(player, currentLevel, levelService, roomService);

        // Initialize the presenter
        this.gamePresenter = new GamePresenter(gameView, player, currentLevel, playerService);
    }

    /**
     * @return the singleton instance of the GameInitializer
     */
    public static synchronized GameInitializer getInstance() {
        if (instance == null) {
            instance = new GameInitializer();
        }
        return instance;
    }

    /**
     * Creates the components needed for running the game loop, then starts it.
     */
    public void startGame() {
        InputHandler inputHandler = new InputHandler(gameView);
        CommandHandler commandHandler = new CommandHandler(playerService);
        GameStateHandler gameStateHandler = new GameStateHandler(player, currentLevel, playerService, levelService, this.gamePresenter);
        GamePresenter gamePresenter = new GamePresenter(gameView, player, currentLevel, playerService);

        // The GameFlowManager orchestrates the entire game
        GameFlowManager gameFlowManager = new GameFlowManager(inputHandler, commandHandler, gamePresenter, gameStateHandler);

        // Start the main game loop
        gameFlowManager.start();
    }
}
