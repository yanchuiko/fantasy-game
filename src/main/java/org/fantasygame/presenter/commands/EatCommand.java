package org.fantasygame.presenter.commands;

import org.fantasygame.model.services.PlayerService;

/**
 * Command to handle eating food in the game.
 */
public class EatCommand implements Command {
    private final String foodName;

    /**
     * Constructs an EatCommand with the specified food name.
     *
     * @param foodName the name of the food to eat
     */
    public EatCommand(String foodName) {
        this.foodName = foodName;
    }

    /**
     * Getter for the food name.
     *
     * @return the name of the food to pick up
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     * Executes the eat command using the provided PlayerService.
     *
     * @param playerService the service to handle player actions
     * @return the result of the eat action
     */
    @Override
    public String execute(PlayerService playerService) {
        return playerService.eat(foodName);
    }
}
