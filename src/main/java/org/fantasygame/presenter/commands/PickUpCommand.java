package org.fantasygame.presenter.commands;

import org.fantasygame.model.services.PlayerService;

/**
 * Command to handle picking up an item in the game.
 */
public class PickUpCommand implements Command {
    private final String itemName;

    /**
     * Constructs a PickUpCommand with the specified item name.
     *
     * @param itemName the name of the item to pick up
     */
    public PickUpCommand(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Getter for the item name.
     *
     * @return the name of the item to pick up
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Executes the pick up command using the provided PlayerService.
     *
     * @param playerService the service to handle player actions
     * @return the result of the pick up action
     */
    @Override
    public String execute(PlayerService playerService) {
        return playerService.pickUp(itemName);
    }
}
