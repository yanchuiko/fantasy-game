package org.fantasygame.presenter.commands;

import org.fantasygame.model.services.PlayerService;

/**
 * Command to handle opening a box in the game.
 */
public class OpenCommand implements Command {
    private final String boxName;

    /**
     * Constructs an OpenCommand with the specified box name.
     *
     * @param boxName the name of the box to open
     */
    public OpenCommand(String boxName) {
        this.boxName = boxName;
    }

    /**
     * Getter for the box name.
     *
     * @return the name of the box to pick up
     */
    public String getBoxName() {
        return boxName;
    }

    /**
     * Executes the open command using the provided PlayerService.
     *
     * @param playerService the service to handle player actions
     * @return the result of the open action
     */
    @Override
    public String execute(PlayerService playerService) {
        return playerService.open(boxName);
    }
}
