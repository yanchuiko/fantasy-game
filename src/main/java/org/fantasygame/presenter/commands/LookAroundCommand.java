package org.fantasygame.presenter.commands;

import org.fantasygame.model.services.PlayerService;

/**
 * Command to handle the player looking around in the game.
 */
public class LookAroundCommand implements Command {

    /**
     * Executes the look around command using the provided PlayerService.
     *
     * @param playerService the service to handle player actions
     * @return the result of the look around action
     */
    @Override
    public String execute(PlayerService playerService) {
        return playerService.lookAround();
    }
}
