package org.fantasygame.presenter.commands;

import org.fantasygame.model.services.PlayerService;

/**
 * Command to drop an item.
 */
public class DropCommand implements Command {
    private final String itemName;

    /**
     * Constructs a DropCommand with the specified item name.
     *
     * @param itemName the name of the item to drop
     */
    public DropCommand(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the name of the item to drop.
     *
     * @return the name of the item
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Executes the drop command using the provided PlayerService.
     *
     * @param playerService the player service to use for dropping the item
     * @return the result of the drop action
     */
    @Override
    public String execute(PlayerService playerService) {
        return playerService.drop(itemName);
    }
}
