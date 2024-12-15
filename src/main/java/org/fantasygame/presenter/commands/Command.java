package org.fantasygame.presenter.commands;

import org.fantasygame.model.services.PlayerService;

/**
 * Command interface represents a command that can be executed in the game.
 */
public interface Command {
    /**
     * Executes the command using the provided PlayerService.
     *
     * @param playerService the player service to use for executing the command
     * @return a string result of the command execution
     */
    String execute(PlayerService playerService);
}
