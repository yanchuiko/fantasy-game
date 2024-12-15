package org.fantasygame.model.entities.content;

import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.content.items.food.*;
import org.fantasygame.model.entities.content.items.potions.*;
import org.fantasygame.model.entities.content.items.spells.*;
import org.fantasygame.model.entities.content.items.boxes.*;
import org.fantasygame.model.entities.content.items.tools.*;

import org.fantasygame.model.entities.content.obstacles.Obstacle;
import org.fantasygame.model.entities.content.obstacles.scientists.*;
import org.fantasygame.model.entities.content.obstacles.traps.*;

import org.fantasygame.presenter.engine.ErrorHandler;

import java.util.*;

/**
 * ContentFactory responsible for generating content (items, obstacles) for rooms in the game.
 * It generates random content based on the current level's probabilities and ensures certain constraints.
 */
public class ContentFactory {

    private final Random random = new Random();

    /**
     * Generates content for a room based on the difficulty level.
     * This content includes items like food, potions, spells, tools, boxes, and obstacles.
     * The room can only have one obstacle, ensuring that it does not exceed this limit.
     *
     * @param level the difficulty level of the game (Level 1, Level 2, Level 3)
     * @return a list of objects (items and/or obstacles) that are generated for the room
     */
    public List<Object> generateRoomContent(String level) {
        List<Object> roomContent = new ArrayList<>();
        Map<String, Double> levelProbabilities = Probabilities.getInstance().DIFFICULTY_PROBABILITIES.get(level);

        // Ensure only one obstacle is added per room
        boolean obstacleAdded = false;

        // Iterate through each category and decide if content should be added based on probability
        for (Map.Entry<String, Double> entry : levelProbabilities.entrySet()) {
            String category = entry.getKey();
            double categoryProbability = entry.getValue();

            if (random.nextDouble() < categoryProbability) {
                Object content = generateContentForCategory(category);
                if (content != null) {
                    // If the content is an obstacle, ensure that only one obstacle is added to the room
                    if (content instanceof Obstacle && !obstacleAdded) {
                        roomContent.add(content);
                        obstacleAdded = true;
                    } else if (!(content instanceof Obstacle)) {
                        roomContent.add(content);
                    }
                }
            }
        }

        return roomContent;
    }

    /**
     * Generates content based on the category provided.
     * It can generate different types of content like items (food, potions, etc.) or obstacles (traps, scientists).
     *
     * @param category the category of content to generate (Food, Potion, Spell, Tool, etc.)
     * @return the generated content (either an Item or an Obstacle)
     */
    private Object generateContentForCategory(String category) {
        Probabilities probabilities = Probabilities.getInstance();

        return switch (category) {
            case "Food" -> getRandomContent(probabilities.FOOD_PROBABILITIES, this::createItem);
            case "Potion" -> getRandomContent(probabilities.POTION_PROBABILITIES, this::createItem);
            case "Spell" -> getRandomContent(probabilities.SPELL_PROBABILITIES, this::createItem);
            case "Tool" -> getRandomContent(probabilities.TOOL_PROBABILITIES, this::createItem);
            case "Box" -> getRandomContent(probabilities.BOX_PROBABILITIES, this::createItem);
            case "Trap" -> getRandomContent(probabilities.TRAP_PROBABILITIES, this::createObstacle);
            case "Scientist" -> getRandomContent(probabilities.SCIENTIST_PROBABILITIES, this::createObstacle);
            default -> null;
        };
    }

    /**
     * Selects a random content item (either item or obstacle) based on predefined probabilities.
     * Uses the provided creator function to create the content based on the selected probability.
     *
     * <p>This method implements a cumulative probability technique:
     * <ul>
     *   <li>A random number (roll) between 0 and 1 is generated to represent the selection threshold.</li>
     *   <li>The probabilities map is iterated, and a running total of cumulative probabilities is maintained.</li>
     *   <li>The roll is compared to the cumulative probability at each step.</li>
     *   <li>The first item where the cumulative probability exceeds the roll is selected.</li>
     * </ul>
     * This ensures that items with higher probabilities have a proportionally greater chance of being selected,
     * while still allowing for randomness.
     *
     * <p>For example, if the probabilities map contains:
     * <pre>{@code
     * {
     *     "ItemA" -> 0.2,
     *     "ItemB" -> 0.5,
     *     "ItemC" -> 0.3
     * }
     * }</pre>
     * and the roll is 0.6:
     * <ul>
     *   <li>ItemA has a cumulative probability of 0.2 (not selected, as 0.6 > 0.2).</li>
     *   <li>ItemB has a cumulative probability of 0.7 (selected, as 0.6 <= 0.7).</li>
     *   <li>ItemC is not considered further as the selection is complete.</li>
     * </ul>
     *
     * @param probabilities the map of content names to their respective probabilities (must sum to 1.0)
     * @param creator       a function to create content items based on their names
     * @param <T>           the type of content (either Item or Obstacle)
     * @return the randomly selected content item, or null if no item is selected
     */
    private <T> T getRandomContent(Map<String, Double> probabilities, ContentCreator<T> creator) {
        double roll = random.nextDouble();
        double cumulativeProbability = 0.0;

        // Iterate through the probabilities to select a content item based on the roll
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (roll < cumulativeProbability) {
                return creator.create(entry.getKey());
            }
        }
        return null;
    }

    /**
     * Creates an item based on the provided item name.
     * This method handles the creation of various item types such as food, potions, tools, and boxes.
     *
     * @param itemName the name of the item to create
     * @return the created item
     */
    private Item createItem(String itemName) {
        return switch (itemName) {
            case "Cake" -> new Cake();
            case "Sandwich" -> new Sandwich();
            case "Pizza" -> new Pizza();
            case "Soup" -> new Soup();
            case "Salad" -> new Salad();
            case "XRay Potion" -> new XRayPotion();
            case "Sleep Potion" -> new SleepPotion();
            case "Health Potion" -> new HealthPotion();
            case "Poison Potion" -> new PoisonPotion();
            case "Lucky Potion" -> new LuckyPotion();
            case "Teleportation Spell" -> new TeleportationSpell();
            case "Freeze Spell" -> new FreezeSpell();
            case "Hammer" -> new Hammer();
            case "Spanner" -> new Spanner();
            case "Alarm Clock" -> new AlarmClock();
            case "Enchanted Box" -> new EnchantedBox();
            case "Treasure Box" -> new TreasureBox();
            case "Pandora Box" -> new PandoraBox();
            default -> {
                ErrorHandler.throwError(200);
                yield null;
            }
        };
    }

    /**
     * Creates an obstacle based on the provided obstacle name.
     * This method handles the creation of different types of obstacles such as traps and scientists.
     *
     * @param obstacleName the name of the obstacle to create
     * @return the created obstacle
     */
    private Obstacle createObstacle(String obstacleName) {
        return switch (obstacleName) {
            case "Needle Trap" -> new NeedleTrap();
            case "Fire Trap" -> new FireTrap();
            case "Poison Trap" -> new PoisonTrap();
            case "Mad Scientist" -> new MadScientist();
            case "Evil Chemist" -> new EvilChemist();
            case "Crazy Wizard" -> new CrazyWizard();
            default -> {
                ErrorHandler.throwError(201);
                yield null;
            }
        };
    }

    /**
     * Functional interface used for creating content objects (either items or obstacles) by their names.
     *
     * @param <T> the type of content being created (either Item or Obstacle)
     */
    @FunctionalInterface
    private interface ContentCreator<T> {
        T create(String name);
    }
}
