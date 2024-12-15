package org.fantasygame.view;

import java.util.Scanner;

/**
 * GameView handles the console view aspects of the game, such as displaying
 * the player's status, room description, map, inventory, and Gandalf's messages.
 * It also facilitates user input for game commands.
 */
public class GameView {

    private final Scanner scanner;  // Scanner object to capture user input from the console
    private String lastMessage;  // Stores the last message to be displayed by Gandalf

    /**
     * Constructor initializes the GameView object, sets up the scanner, and
     * initializes the welcome message.
     */
    public GameView() {
        this.scanner = new Scanner(System.in);
        this.lastMessage = """
                Welcome to the Treasure Dungeon!\s
                I am Gandalf the Grey, and I will be your guide.\s
                And yes, in this dungeon... You SHALL pass!""";
    }

    /**
     * Displays the current status of the game, including the player's position, room description, map,
     * power points, and inventory.
     *
     * @param playerPosition The current position of the player.
     * @param roomDescription A description of the current room.
     * @param map The rendered map of the current level.
     * @param powerPoints The current power points of the player.
     * @param inventory The formatted string of the player's inventory.
     */
    public void displayGameStatus(String playerPosition, String roomDescription, String map, int powerPoints, String inventory) {
        // Print game title
        System.out.println("-------------------------------------------------");
        System.out.println("The Lord of the Rings: Treasure Dungeon");
        System.out.println("-------------------------------------------------");

        // Player's position display
        System.out.println("Position: " + playerPosition);
        System.out.println("-------------------------------------------------");

        // Room description display
        System.out.println("Description: " + roomDescription);
        System.out.println("-------------------------------------------------");

        // Map display
        System.out.println("Map:");
        System.out.println(map);
        System.out.println("-------------------------------------------------");

        // Power points display
        System.out.println("Power Points: " + powerPoints);
        System.out.println("-------------------------------------------------");

        // Inventory display
        System.out.println("Inventory:");
        System.out.println(inventory.isEmpty() ? "No items in inventory." : inventory);
        System.out.println("-------------------------------------------------");

        // Gandalf's message display
        displayGandalfMessage();
    }

    /**
     * Displays Gandalf's message. If the message is empty, prompts the player to take an action.
     */
    private void displayGandalfMessage() {
        // Display Gandalf's message or prompt if no message is set
        if (lastMessage.isEmpty()) {
            System.out.println("Gandalf: What do you want to do?");
        } else {
            System.out.println("Gandalf: " + lastMessage);
        }
        lastMessage = "";  // Reset the message after displaying it
    }

    /**
     * Prompts the user for input and returns the entered command as a trimmed string.
     *
     * @return The user's input as a string.
     */
    public String getUserInput() {
        // Prompt for user input and return the trimmed result
        System.out.println("-------------------------------------------------");
        System.out.println("What do you want to do?");
        System.out.print("Enter command: ");
        return scanner.nextLine().trim();
    }

    /**
     * Updates Gandalf's last message that will be displayed in the game view.
     *
     * @param message The new message to be displayed by Gandalf.
     */
    public void updateMessage(String message) {
        this.lastMessage = message;
    }

    /**
     * Updates Gandalf's message to indicate that the player has won the game.
     */
    public void updateMessageGameWon() {
        this.lastMessage = "Congratulations! You have found the One Ring, the Ring of Power :)\n-------------------------------------------------";
    }

    /**
     * Updates Gandalf's message to indicate that the player has lost the game.
     */
    public void updateMessageGameOver() {
        this.lastMessage = "Oops! Well, it didn’t go as I planned. You lost :(\n-------------------------------------------------";
    }

    /**
     * Updates Gandalf's message to indicate that the player has found the exit.
     */
    public void updateMessageFoundExit() {
        this.lastMessage = "You found the exit! Proceeding to the next level.";
    }

    /**
     * Renders the map for the game, showing the player's position marked by "P" and
     * other rooms using the respective symbols.
     *
     * @param mapGrid The 2D array representing the grid of rooms.
     * @param playerRow The current row of the player.
     * @param playerCol The current column of the player.
     * @return The rendered map as a string.
     */
    public String renderMap(String[][] mapGrid, int playerRow, int playerCol) {
        StringBuilder renderedMap = new StringBuilder();

        // Iterate through the map grid and render it
        for (int row = 0; row < mapGrid.length; row++) {
            for (int col = 0; col < mapGrid[row].length; col++) {
                // Mark player's position with "P"
                if (row == playerRow && col == playerCol) {
                    renderedMap.append("P ");
                } else {
                    // Append the room symbol to the rendered map (already visited rooms)
                    renderedMap.append(mapGrid[row][col]).append(" ");
                }
            }
            renderedMap.append("\n");
        }

        // Return the rendered map
        return renderedMap.toString().trim();
    }

    /**
     * Clears the console screen by printing blank lines. Useful for clearing the display
     * between game updates.
     */
    public void clearScreen() {
        // Print blank lines to clear the screen
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }
}
