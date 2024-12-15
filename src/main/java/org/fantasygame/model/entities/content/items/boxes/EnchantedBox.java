package org.fantasygame.model.entities.content.items.boxes;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.content.items.spells.Spell;
import org.fantasygame.model.entities.content.items.spells.TeleportationSpell;
import org.fantasygame.model.entities.content.items.spells.FreezeSpell;
import org.fantasygame.model.entities.core.Player;

import java.util.Random;

/**
 * Class representing an Enchanted Box item in the game.
 */
public class EnchantedBox extends Box {

    private final Random random;

    /**
     * Constructs an Enchanted Box item with default dependencies.
     */
    public EnchantedBox() {
        this(new Random());
    }

    /**
     * Constructs an Enchanted Box item with a specified Random instance.
     *
     * @param random the Random instance to use for generating random numbers
     */
    public EnchantedBox(Random random) {
        super("Enchanted Box");
        this.random = random;
    }

    /**
     * Uses the Enchanted Box item on the specified player.
     *
     * @param player the player on whom the Enchanted Box item is used
     * @return a string describing the result of using the Enchanted Box item
     */
    @Override
    public String use(Player player) {
        Spell randomSpell = generateRandomSpell(); // Generate random spell

        // Check if player has space for the spell (limit 2)
        if (player.getInventory().checkSpellLimit()) {
            player.getInventory().addItem(randomSpell);
        } else {
            ErrorHandler.throwError(300);
        }
        return "Enchanted box opened, you found a random spell!";
    }

    /**
     * Generates a random spell from the available options.
     *
     * @return a random spell
     */
    private Spell generateRandomSpell() {
        return switch (random.nextInt(2)) {
            case 0 -> new FreezeSpell();
            case 1 -> new TeleportationSpell();
            default -> throw new IllegalStateException("Unexpected spell generation");
        };
    }
}
