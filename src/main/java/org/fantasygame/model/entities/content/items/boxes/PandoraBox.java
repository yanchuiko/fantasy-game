package org.fantasygame.model.entities.content.items.boxes;

import org.fantasygame.model.entities.content.items.potions.*;
import org.fantasygame.model.entities.core.Player;

import java.util.Random;

/**
 * Class representing a Pandora Box item in the game.
 */
public class PandoraBox extends Box {

    private final Random random;

    /**
     * Constructs a Pandora Box item with a default Random instance.
     */
    public PandoraBox() {
        this(new Random());
    }

    /**
     * Constructs a Pandora Box item with a specified Random instance.
     *
     * @param random the Random instance to use for generating random numbers
     */
    public PandoraBox(Random random) {
        super("Pandora Box");
        this.random = random;
    }

    /**
     * Uses the Pandora Box item on the specified player.
     *
     * @param player the player on whom the Pandora Box item is used
     * @return a string describing the result of using the Pandora Box item
     */
    @Override
    public String use(Player player) {
        Potion randomPotion = generateRandomPotion(); // Generate random potion
        player.getInventory().addItem(randomPotion); // Add potion to player's inventory
        return "Pandora box opened, you found a random potion!";
    }

    /**
     * Generates a random potion from the available options.
     *
     * @return a random potion
     */
    private Potion generateRandomPotion() {
        return switch (random.nextInt(5)) {
            case 0 -> new HealthPotion();
            case 1 -> new PoisonPotion();
            case 2 -> new LuckyPotion();
            case 3 -> new SleepPotion();
            case 4 -> new XRayPotion();
            default -> throw new IllegalStateException("Unexpected potion generation");
        };
    }
}
