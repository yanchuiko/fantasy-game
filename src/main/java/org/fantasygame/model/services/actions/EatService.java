package org.fantasygame.model.services.actions;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.content.items.food.Food;
import org.fantasygame.model.entities.core.Player;

public class EatService {

    private final Player player;

    /**
     * Constructs an EatService with the necessary dependencies.
     *
     * @param player the player instance
     */
    public EatService(Player player) {
        this.player = player;
    }

    /**
     * Allows the player to eat a food item from their inventory.
     *
     * @param foodName the name of the food item to eat
     * @return a feedback message indicating the food item was eaten and how many power points were gained
     */
    public String eat(String foodName) {
        Item item = player.getInventory().getItem(foodName);

        // Ensure the item is a food type.
        if (!(item instanceof Food)) {
            ErrorHandler.throwError(800);
        }

        // Use the food item to restore power points.
        Food food = (Food) item;
        food.use(player);

        // Remove the food item from the inventory.
        player.getInventory().removeItem(foodName);

        return "You ate " + food.getName() + " and gained " + food.getPowerPoints() + " Power Points!";
    }
}
