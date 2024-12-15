package org.fantasygame.presenter.engine;

import org.fantasygame.view.GameView;

/**
 * The InputHandler class is responsible for retrieving player input from the GameView.
 */
public class InputHandler {

    private final GameView gameView;

    /**
     * Constructs a new InputHandler.
     *
     * @param gameView the GameView from which this handler will retrieve user input
     */
    public InputHandler(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Retrieves input from the user
     *
     * @return the trimmed input string entered by the user
     */
    public String getInput() {
        return gameView.getUserInput();
    }
}
