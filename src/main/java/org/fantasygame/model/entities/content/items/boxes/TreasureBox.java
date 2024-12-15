package org.fantasygame.model.entities.content.items.boxes;

import org.fantasygame.model.entities.core.Player;

import java.util.Random;

/**
 * Class representing a Treasure Box item in the game.
 */
public class TreasureBox extends Box {

    private static final int[] POWER_POINT_OPTIONS = {25, 50, 75};
    private int randomPowerPoints;
    private final Random random;

    /**
     * Constructs a Treasure Box item with a new Random instance.
     */
    public TreasureBox() {
        this(new Random());
    }

    /**
     * Constructs a Treasure Box item with a specified Random instance.
     *
     * @param random the Random instance to use
     */
    public TreasureBox(Random random) {
        super("Treasure Box");
        this.random = random;
    }

    /**
     * Uses the Treasure Box item on the specified player.
     *
     * @param player the player on whom the Treasure Box item is used
     * @return a string describing the result of using the Treasure Box item
     */
    @Override
    public String use(Player player) {
        randomPowerPoints = getRandomPowerPoints();
        player.setPowerPoints(player.getPowerPoints() + randomPowerPoints);
        return "Treasure box opened, you found " + getChosenPowerPoints() + " power points!";
    }

    /**
     * Generates a random number of power points from the available options.
     *
     * @return a random number of power points
     */
    private int getRandomPowerPoints() {
        return POWER_POINT_OPTIONS[random.nextInt(POWER_POINT_OPTIONS.length)];
    }

    /**
     * Gets the chosen number of power points.
     *
     * @return the chosen number of power points
     */
    public int getChosenPowerPoints() {
        return randomPowerPoints;
    }
}
