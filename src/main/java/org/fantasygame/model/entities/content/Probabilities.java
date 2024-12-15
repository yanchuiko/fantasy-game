package org.fantasygame.model.entities.content;

import java.util.Map;

/**
 * Probabilities is a Singleton class that manages the probabilities for different game items/obstacles based on the level difficulty.
 */
public class Probabilities {

    private static Probabilities instance;

    /**
     * Returns the singleton instance of the Probabilities class.
     * This method ensures that only one instance of the class is created.
     *
     * @return the singleton instance of Probabilities
     */
    public static synchronized Probabilities getInstance() {
        if (instance == null) {
            instance = new Probabilities();
        }
        return instance;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Level Difficulty Settings

    /**
     * Maps each level to a set of probabilities for different types of items and obstacles.
     * The probabilities are used to determine the likelihood of these items appearing in the game at each level.
     */
    public final Map<String, Map<String, Double>> DIFFICULTY_PROBABILITIES = Map.of(
            "Level 1", Map.of(
                    "Food", 0.40,
                    "Potion", 0.30,
                    "Spell", 0.15,
                    "Tool", 0.25,
                    "Box", 0.15,
                    "Trap", 0.20,
                    "Scientist", 0.20
            ),
            "Level 2", Map.of(
                    "Food", 0.20,
                    "Potion", 0.20,
                    "Spell", 0.30,
                    "Tool", 0.20,
                    "Box", 0.30,
                    "Trap", 0.30,
                    "Scientist", 0.30
            ),
            "Level 3", Map.of(
                    "Food", 0.10,
                    "Potion", 0.20,
                    "Spell", 0.10,
                    "Tool", 0.25,
                    "Box", 0.25,
                    "Trap", 0.25,
                    "Scientist", 0.40
            )
    );

    // -----------------------------------------------------------------------------------------------------------------
    // Items & Obstacles Probabilities
    /**
     * Probabilities for different food items appearing in the game.
     */
    public final Map<String, Double> FOOD_PROBABILITIES = Map.of(
            "Salad", 0.30,
            "Cake", 0.25,
            "Sandwich", 0.20,
            "Soup", 0.15,
            "Pizza", 0.10
    );

    /**
     * Probabilities for different potion items appearing in the game.
     */
    public final Map<String, Double> POTION_PROBABILITIES = Map.of(
            "Health Potion", 0.25,
            "Poison Potion", 0.25,
            "Lucky Potion", 0.25,
            "Sleep Potion", 0.125,
            "XRay Potion", 0.125
    );

    /**
     * Probabilities for different spell items appearing in the game.
     */
    public final Map<String, Double> SPELL_PROBABILITIES = Map.of(
            "Teleportation Spell", 0.60,
            "Freeze Spell", 0.40
    );

    /**
     * Probabilities for different tool items appearing in the game.
     */
    public final Map<String, Double> TOOL_PROBABILITIES = Map.of(
            "Spanner", 0.40,
            "Alarm Clock", 0.35,
            "Hammer", 0.25
    );

    /**
     * Probabilities for different box items appearing in the game.
     */
    public final Map<String, Double> BOX_PROBABILITIES = Map.of(
            "Pandora Box", 0.40,
            "Enchanted Box", 0.35,
            "Treasure Box", 0.25
    );

    /**
     * Probabilities for different traps appearing in the game.
     */
    public final Map<String, Double> TRAP_PROBABILITIES = Map.of(
            "Needle Trap", 0.30,
            "Fire Trap", 0.35,
            "Poison Trap", 0.35
    );

    /**
     * Probabilities for different scientists appearing in the game.
     */
    public final Map<String, Double> SCIENTIST_PROBABILITIES = Map.of(
            "Mad Scientist", 0.45,
            "Evil Chemist", 0.35,
            "Crazy Wizard", 0.20
    );
}
