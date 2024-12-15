package org.fantasygame.presenter.engine;

import org.fantasygame.presenter.commands.Command;
import org.fantasygame.presenter.commands.CommandFactory;
import org.fantasygame.model.services.PlayerService;

/**
 * Processes user input by creating and executing corresponding commands.
 */
public class CommandHandler {
    private final PlayerService playerService;

    /**
     * @param playerService the service used by commands to interact with the player's state
     */
    public CommandHandler(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Creates a command from the given input string and executes it.
     *
     * @param userInput the user's input string
     * @return the result of executing the command, or an error message if invalid
     */
    public String process(String userInput) {
        try {
            Command command = CommandFactory.createCommand(userInput);
            return command.execute(playerService);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
