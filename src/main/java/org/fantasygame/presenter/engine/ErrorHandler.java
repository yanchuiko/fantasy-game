package org.fantasygame.presenter.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling error messages and throwing exceptions based on error codes.
 */
public class ErrorHandler {

    // Map of error codes to error messages
    private static final Map<Integer, String> ERROR_MESSAGES = new HashMap<>();

    // Initialize the error messages
    static {
        // General errors
        ERROR_MESSAGES.put(100, "Invalid command");
        ERROR_MESSAGES.put(101, "Unexpected arguments for this command");
        ERROR_MESSAGES.put(102, "Missing argument for this command");

        // Content errors
        ERROR_MESSAGES.put(200, "Unknown item");
        ERROR_MESSAGES.put(201, "Unknown obstacle");

        // Inventory errors
        ERROR_MESSAGES.put(300, "Inventory is full");
        ERROR_MESSAGES.put(301, "You don't have this item in your inventory");

        // Level errors
        ERROR_MESSAGES.put(400, "Unknown map symbol");
        ERROR_MESSAGES.put(401, "Invalid level number");

        // Movement errors
        ERROR_MESSAGES.put(500, "You cannot move outside the map");
        ERROR_MESSAGES.put(501, "Wall blocking the way");

        // Room errors
        ERROR_MESSAGES.put(600, "The room is empty");
        ERROR_MESSAGES.put(601, "There is no such box in this room");

        // Pick up errors
        ERROR_MESSAGES.put(700, "There is nothing to pick up here");
        ERROR_MESSAGES.put(701, "There is no such item in this room");
        ERROR_MESSAGES.put(702, "You cannot pick up boxes. You need to use the Spanner to open it");
        ERROR_MESSAGES.put(703, "You cannot carry any more items of this type");

        // Command errors
        ERROR_MESSAGES.put(800, "You cannot eat this item. EAT command only works with FOOD items.");
        ERROR_MESSAGES.put(801, "You cannot drink this item. DRINK command only works with POTION items.");
        ERROR_MESSAGES.put(802, "You cannot use this item. USE command only works with SPELL items.");

        // Item errors
        ERROR_MESSAGES.put(900, "You need a Spanner to open this box");
        ERROR_MESSAGES.put(901, "You cannot manually use the Freeze Spell. It is automatically activated when you enter a room with obstacles.");
    }

    /**
     * Throws an IllegalArgumentException with the error message corresponding to the given error code.
     *
     * @param errorCode the error code for which to throw the exception
     * @throws IllegalArgumentException with the error message corresponding to the error code
     */
    public static void throwError(int errorCode) {
        String errorMessage = getErrorMessage(errorCode);
        throw new IllegalArgumentException(errorMessage);
    }

    /**
     * Retrieves the error message corresponding to the given error code.
     *
     * @param errorCode the error code for which to retrieve the message
     * @return the error message corresponding to the error code
     */
    public static String getErrorMessage(int errorCode) {
        return ERROR_MESSAGES.getOrDefault(errorCode, "Unknown error occurred.");
    }
}
