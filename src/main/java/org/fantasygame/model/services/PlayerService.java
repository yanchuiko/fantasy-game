package org.fantasygame.model.services;

import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.actions.*;

/**
 * PlayerService orchestrates player actions.
 */
public class PlayerService {

    private final Player player;
    private Level currentLevel;
    private final LevelService levelService;

    // Action services handle specific player actions
    private final MovementService movementService;
    private final LookAroundService lookAroundService;
    private final PickUpService pickUpService;
    private final EatService eatService;
    private final DrinkService drinkService;
    private final UseService useService;
    private final DropService dropService;
    private final OpenService openService;

    /**
     * @param player        the player being managed
     * @param currentLevel  the current level where the player is located
     * @param levelService  handles visibility and transitions between levels
     * @param roomService   used by action services to manipulate rooms
     */
    public PlayerService(Player player, Level currentLevel, LevelService levelService, RoomService roomService) {
        this.player = player;
        this.currentLevel = currentLevel;
        this.levelService = levelService;

        // Set the current level for the player
        player.setCurrentLevel(currentLevel);

        // Initialize action-specific services
        this.movementService = new MovementService(player, levelService, roomService);
        this.lookAroundService = new LookAroundService(this);
        this.pickUpService = new PickUpService(player, this, roomService);
        this.eatService = new EatService(player);
        this.drinkService = new DrinkService(player);
        this.useService = new UseService(player, roomService);
        this.dropService = new DropService(player, this, roomService);
        this.openService = new OpenService(player, this, roomService);

        // Initialize player position and visibility
        initializePlayerPosition();
    }

    /**
     * Moves the player in the specified direction.
     */
    public String move(String direction) {
        return movementService.move(direction);
    }

    /**
     * Provides a description of the room.
     */
    public String lookAround() {
        return lookAroundService.lookAround();
    }

    /**
     * Picks up an item from the current room.
     */
    public String pickUp(String itemName) {
        return pickUpService.pickUp(itemName);
    }

    /**
     * Eats a food item to restore power points.
     */
    public String eat(String foodName) {
        return eatService.eat(foodName);
    }

    /**
     * Drinks a potion for various effects.
     */
    public String drink(String potionName) {
        return drinkService.drink(potionName);
    }

    /**
     * Uses a spell to interact with the level.
     */
    public String use(String spellName) {
        return useService.use(spellName);
    }

    /**
     * Drops an item into the current room.
     */
    public String drop(String itemName) {
        return dropService.drop(itemName);
    }

    /**
     * Opens a box in the current room.
     */
    public String open(String boxName) {
        return openService.open(boxName);
    }

    /**
     * @return the current Room the player is in
     */
    public Room getPlayerCurrentRoom() {
        return currentLevel.getRoom(player.getRow(), player.getCol());
    }

    /**
     * Finds an item by name in a given room.
     */
    public Item findItemInRoom(Room room, String itemName) {
        // Ensure the room is not null and has content
        if (room == null || room.getContent().isEmpty()) {
            return null;
        }

        // Search for the item by name
        for (Object obj : room.getContent()) {
            if (obj instanceof Item) {
                Item item = (Item) obj;
                if (item.getName().equalsIgnoreCase(itemName)) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * Initializes the player's position and visibility of the room.
     */
    private void initializePlayerPosition() {
        Room startingRoom = getPlayerCurrentRoom();
        if (startingRoom != null) {
            startingRoom.setVisited(true);
            startingRoom.setRevealed(true);
            levelService.revealAdjacentRooms(currentLevel, player.getRow(), player.getCol());
        }
    }

    /**
     * Updates the current level and reinitialized the player's position and visibility.
     */
    public void updateLevel(Level newLevel) {
        this.currentLevel = newLevel;
        player.setCurrentLevel(newLevel);
        initializePlayerPosition();
    }

    /**
     * @return a description of walls or exits around the player
     */
    public String getRoomDescription() {
        StringBuilder description = new StringBuilder();
        Room[][] grid = currentLevel.getGrid();
        int playerRow = player.getRow();
        int playerCol = player.getCol();

        // Check surrounding directions for walls
        if (playerRow > 0 && "wall".equals(grid[playerRow - 1][playerCol].getType())) description.append("Wall in front. ");
        if (playerRow < grid.length - 1 && "wall".equals(grid[playerRow + 1][playerCol].getType())) description.append("Wall behind. ");
        if (playerCol > 0 && "wall".equals(grid[playerRow][playerCol - 1].getType())) description.append("Wall to the left. ");
        if (playerCol < grid[playerRow].length - 1 && "wall".equals(grid[playerRow][playerCol + 1].getType())) description.append("Wall to the right. ");

        // Check surrounding directions for exits
        if (playerRow > 0 && "exit".equals(grid[playerRow - 1][playerCol].getType())) description.append("Exit in front. ");
        if (playerRow < grid.length - 1 && "exit".equals(grid[playerRow + 1][playerCol].getType())) description.append("Exit behind. ");
        if (playerCol > 0 && "exit".equals(grid[playerRow][playerCol - 1].getType())) description.append("Exit to the left. ");
        if (playerCol < grid[playerRow].length - 1 && "exit".equals(grid[playerRow][playerCol + 1].getType())) description.append("Exit to the right. ");

        return description.isEmpty() ? "Nothing around this room" : description.toString().trim();
    }
}
