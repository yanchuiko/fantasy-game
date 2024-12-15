package org.fantasygame.model.entities.core;

import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.content.items.potions.Potion;
import org.fantasygame.model.entities.content.items.spells.Spell;
import org.fantasygame.model.entities.content.items.tools.Tool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the Inventory of a player.
 * The inventory has a limit on the number of spells and tools it can hold (2).
 */
public class Inventory {
    private static final int LIMIT = 2;
    private int spellCount = 0;
    private int toolCount = 0;
    private final Map<String, Item> items;
    private final Map<String, Integer> itemCounts;
    private final List<Potion> potionInstances;

    /**
     * Constructs an empty inventory.
     */
    public Inventory() {
        this.items = new LinkedHashMap<>(); // I am using LinkedHashMap to maintain insertion order
        this.itemCounts = new LinkedHashMap<>(); // Same here
        this.potionInstances = new ArrayList<>(); // Using instance for Potions, because they all have the same name
    }

    /**
     * Checks if the inventory can hold more spells.
     *
     * @return true if the inventory can hold more spells, false otherwise
     */
    public boolean checkSpellLimit() {
        return spellCount < LIMIT;
    }

    /**
     * Checks if the inventory can hold more tools.
     *
     * @return true if the inventory can hold more tools, false otherwise
     */
    public boolean checkToolLimit() {
        return toolCount < LIMIT;
    }

    /**
     * Normalizes the item name to lowercase.
     *
     * @param itemName the name of the item
     * @return the normalized item name
     */
    private String normalize(String itemName) {
        return itemName.toLowerCase();
    }

    /**
     * Checks if a tool of the specified class is in the inventory.
     *
     * @param toolClass the class of the tool
     * @return true if the tool is in the inventory, false otherwise
     */
    private boolean isToolInInventory(Class<Tool> toolClass) {
        for (Item item : items.values()) {
            if (item instanceof Tool && toolClass.isInstance(item)) {
                    return true;
                }
            }
        return false;
    }

    /**
     * Checks if the inventory contains an item with the specified name.
     *
     * @param itemName the name of the item
     * @return true if the item is in the inventory, false otherwise
     */
    public boolean contains(String itemName) {
        return items.containsKey(normalize(itemName));
    }

    /**
     * Retrieves an item from the inventory by its name.
     *
     * @param itemName the name of the item
     * @return the item if found, null otherwise
     */
    public Item getItem(String itemName) {
        return items.get(normalize(itemName));
    }

    /**
     * Formats the inventory to display a readable list of items with their counts.
     * Each item is listed with its name and the number of copies the player possesses.
     *
     * @return a string representing the player's inventory, or a message indicating it is empty
     */
    public String getFormattedInventory() {
        // Check if the inventory is empty
        if (items.isEmpty()) {
            return "No items in inventory.";
        }

        // Create a StringBuilder to store the formatted inventory
        StringBuilder inventory = new StringBuilder();

        // Loop through all items in the inventory
        for (Map.Entry<String, Item> entry : items.entrySet()) {
            String itemName;

            // Special handling for "potion" items
            if (entry.getKey().equals("potion")) {
                itemName = "Potion";
            } else {
                itemName = entry.getValue().getName();
            }

            // Get the count of the item
            int itemCount = itemCounts.get(entry.getKey());

            // Format the item display as "- ItemName (x1)"
            inventory.append("- ").append(itemName)
                    .append(" (x").append(itemCount).append(")\n");
        }

        // Return the formatted inventory as a string
        return inventory.toString().trim();
    }

    /**
     * Retrieves the first potion from the inventory (if any).
     *
     * @return the first potion in the inventory, or null if there are no potions
     */
    public Potion getPotionInstance() {
        if (!potionInstances.isEmpty()) {
            return potionInstances.get(0);
        }
        return null;
    }

    /**
     * Adds an item to the inventory.
     * The method handles different item types such as potions, tools, and spells,
     * ensuring that limits for spell and tool counts are respected.
     *
     * @param item the item to be added to the inventory
     * @return true if the item was successfully added, false otherwise
     */
    public boolean addItem(Item item) {
        String normalizedItem;

        // Special handling for potions, where they are grouped under the key "potion"
        if (item instanceof Potion) {
            normalizedItem = "potion";
        } else {
            normalizedItem = normalize(item.getName());
        }

        // Handle adding a spell, ensuring the spell limit is not exceeded
        if (item instanceof Spell) {
            if (!checkSpellLimit()) {
                return false;
            }
            spellCount++;
        }
        // Handle adding a tool, ensuring the tool limit is not exceeded
        else if (item instanceof Tool) {
            Class<?> itemClass = item.getClass();

            // Return false if the tool limit has been reached
            if (!checkToolLimit()) {
                return false;
            }

            // Return false if the tool already exists in the inventory
            if (isToolInInventory((Class<Tool>) itemClass)) {
                return false;
            }

            toolCount++;
        }

        // Special handling for potions (potion instances are managed separately)
        if (item instanceof Potion) {
            potionInstances.add((Potion) item);
        }

        // Update item count and add the item to the inventory if it's not already present
        itemCounts.put(normalizedItem, itemCounts.getOrDefault(normalizedItem, 0) + 1);
        items.putIfAbsent(normalizedItem, item);

        return true;
    }

    /**
     * Removes an item from the inventory by its name.
     * The method updates the counts accordingly and removes the item if the count reaches zero.
     *
     * @param itemName the name of the item to be removed
     * @return the removed item, or null if the item was not found
     */
    public Item removeItem(String itemName) {
        String normalizedItem = normalize(itemName);

        // Return null if the item is not in the inventory
        if (!items.containsKey(normalizedItem)) {
            return null;
        }

        Item removedItem = items.get(normalizedItem); // Retrieve the item to be removed
        int count = itemCounts.get(normalizedItem); // Get the current count of the item

        // Decrease the count and remove the item if the count reaches zero
        if (count > 1) {
            itemCounts.put(normalizedItem, count - 1);
        } else {
            items.remove(normalizedItem);
            itemCounts.remove(normalizedItem);
        }

        // Handle removing spell or tool specific items
        if (removedItem instanceof Spell) {
            spellCount = Math.max(0, spellCount - 1);
        } else if (removedItem instanceof Tool) {
            toolCount = Math.max(0, toolCount - 1);
        } else if (removedItem instanceof Potion potion) {
            potionInstances.remove(potion); // Remove the potion from the potion instances list
        }

        return removedItem;
    }

    /**
     * Removes a potion from the inventory and updates the counts.
     * This method is called when a potion is used or discarded.
     *
     * @param potion the potion to be removed
     */
    public void removePotion(Potion potion) {
        if (potionInstances.remove(potion)) { // Remove the potion from the potionInstances list
            String normalizedItemName = normalize("potion");
            int count = itemCounts.getOrDefault(normalizedItemName, 0);

            // Decrease the count and remove the potion item if the count reaches zero
            if (count > 1) {
                itemCounts.put(normalizedItemName, count - 1);
            } else {
                items.remove(normalizedItemName);
                itemCounts.remove(normalizedItemName);
            }
        }
    }
}