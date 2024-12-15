package org.fantasygame.app;

import org.fantasygame.presenter.GameInitializer;

/**
 * SID: 2218952
 * Project Name: Fantasy Game
 * This is the main entry point of application.
 * It initializes the GameInitializer and starts the game.
 */
public class Main {
    /**
     * The main method to start the Fantasy Game application.
     */
    public static void main(String[] args) {
        GameInitializer gameInitializer = GameInitializer.getInstance();
        gameInitializer.startGame();
    }
}
