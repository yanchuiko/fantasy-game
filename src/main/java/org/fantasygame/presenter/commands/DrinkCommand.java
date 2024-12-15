package org.fantasygame.presenter.commands;

import org.fantasygame.model.services.PlayerService;

/**
 * Command to handle drinking a potion in the game.
 */
public class DrinkCommand implements Command {
    private final String potionName;

    /**
     * Constructs a DrinkCommand with the specified potion name.
     *
     * @param potionName the name of the potion to drink
     */
    public DrinkCommand(String potionName) {
        this.potionName = potionName;
    }

    /**
     * Getter for the potion name.
     *
     * @return the name of the potion to pick up
     */
    public String getPotionName() {
        return potionName;
    }

    /**
     * Executes the drink command using the provided PlayerService.
     *
     * @param playerService the service to handle player actions
     * @return the result of the drink action
     */
    @Override
    public String execute(PlayerService playerService) {
        return playerService.drink(potionName);
    }
}
