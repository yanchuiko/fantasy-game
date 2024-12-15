package org.fantasygame.model.services.actions;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.content.items.potions.Potion;
import org.fantasygame.model.entities.core.Player;

public class DrinkService {

    private final Player player;

    /**
     * Constructs a DrinkService with the necessary dependencies.
     *
     * @param player the player instance
     */
    public DrinkService(Player player) {
        this.player = player;
    }

    /**
     * Allows the player to drink a potion from their inventory.
     *
     * @param potionName the name of the potion to drink
     * @return a feedback message indicating the potion's effect
     */
    public String drink(String potionName) {
        // Check if the player has the specified potion in their inventory (by instance because all potions have same name)
        Potion potion = player.getInventory().getPotionInstance();

        // Validate if the potion exists in the inventory
        if (potion == null || !player.getInventory().contains(potionName)) {
            ErrorHandler.throwError(801);
        }

        // Use the potion and remove it from the inventory
        String message = potion.use(player);
        player.getInventory().removePotion(potion);

        return message;
    }
}
