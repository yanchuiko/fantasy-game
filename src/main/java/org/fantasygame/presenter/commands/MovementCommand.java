package org.fantasygame.presenter.commands;

import org.fantasygame.model.services.PlayerService;

/**
 * Command to handle player movement in a specified direction.
 */
public class MovementCommand implements Command {
    private final String direction;

    /**
     * Constructs a MovementCommand with the specified direction.
     *
     * @param direction the direction in which the player should move
     */
    public MovementCommand(String direction) {
        this.direction = direction;
    }

    /**
     * Gets the direction of the movement.
     *
     * @return the direction of the movement
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Executes the movement command using the provided PlayerService.
     *
     * @param playerService the service to handle player actions
     * @return the result of the movement action
     */
    @Override
    public String execute(PlayerService playerService) {
        return playerService.move(direction);
    }
}
