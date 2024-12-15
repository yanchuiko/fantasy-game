package org.fantasygame.presenter.commands;

import org.fantasygame.presenter.engine.ErrorHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * CommandFactory class creates commands based on input strings.
 * It maps user input (aliases) to corresponding command actions.
 */
public class CommandFactory {

    // Map of command keys to their aliases
    private static final Map<String, List<String>> COMMAND_ALIASES = new HashMap<>();

    // Initialize the map with command keys and their possible aliases
    static {
        COMMAND_ALIASES.put("moveForward", List.of("move forward", "go forward", "forward", "w"));
        COMMAND_ALIASES.put("moveBack", List.of("move back", "go back", "back", "s"));
        COMMAND_ALIASES.put("moveRight", List.of("move right", "go right", "right", "d"));
        COMMAND_ALIASES.put("moveLeft", List.of("move left", "go left", "left", "a"));
        COMMAND_ALIASES.put("lookAround", List.of("look around", "search", "l"));
        COMMAND_ALIASES.put("pickUp", List.of("pick up", "take", "p"));
        COMMAND_ALIASES.put("eat", List.of("eat", "e"));
        COMMAND_ALIASES.put("drink", List.of("drink", "dr"));
        COMMAND_ALIASES.put("use", List.of("use", "activate"));
        COMMAND_ALIASES.put("drop", List.of("drop", "leave"));
        COMMAND_ALIASES.put("open", List.of("open", "unlock"));
    }

    // Map of command keys to functions that create the command
    private static final Map<String, Function<String, Command>> COMMAND_CREATORS = new HashMap<>();

    // Initialize the map with the command keys and their respective functions
    static {
        COMMAND_CREATORS.put("moveForward", arg -> new MovementCommand("forward"));
        COMMAND_CREATORS.put("moveBack", arg -> new MovementCommand("back"));
        COMMAND_CREATORS.put("moveRight", arg -> new MovementCommand("right"));
        COMMAND_CREATORS.put("moveLeft", arg -> new MovementCommand("left"));
        COMMAND_CREATORS.put("lookAround", arg -> new LookAroundCommand());
        COMMAND_CREATORS.put("pickUp", PickUpCommand::new);
        COMMAND_CREATORS.put("eat", EatCommand::new);
        COMMAND_CREATORS.put("drink", DrinkCommand::new);
        COMMAND_CREATORS.put("use", UseCommand::new);
        COMMAND_CREATORS.put("drop", DropCommand::new);
        COMMAND_CREATORS.put("open", OpenCommand::new);
    }

    /**
     * Creates a command based on the input string.
     * It matches the input string with known commands or their aliases and creates the corresponding command object.
     *
     * @param input the input string representing the command
     * @return the created command object
     */
    public static Command createCommand(String input) {
        // Normalize the input string
        String normalizedInput = input.trim().toLowerCase();

        // Loop through all command aliases to find a match with the input
        for (Map.Entry<String, List<String>> entry : COMMAND_ALIASES.entrySet()) {
            String commandKey = entry.getKey();
            List<String> aliases = entry.getValue();

            // Loop through the list of aliases for the current command key
            for (String alias : aliases) {
                // Check if the input matches the alias or starts with the alias followed by a space
                if (normalizedInput.equals(alias) || normalizedInput.startsWith(alias + " ")) {
                    // Extract the argument from the input string
                    String argument = normalizedInput.equals(alias)
                            ? "" // No argument if the input matches exactly
                            : normalizedInput.substring(alias.length()).trim(); // Extract the argument

                    // Check if the command requires an argument
                    boolean requiresArgument = List.of("pickUp", "eat", "drink", "use", "drop", "open").contains(commandKey);

                    // Validate the argument based on whether it's required or not
                    if (!requiresArgument && !argument.isEmpty()) {
                        ErrorHandler.throwError(101);
                    } else if (requiresArgument && argument.isEmpty()) {
                        ErrorHandler.throwError(102);
                    }

                    // Create and return the command using the command creator for the matched command key
                    return COMMAND_CREATORS.get(commandKey).apply(argument);
                }
            }
        }

        // If no match was found, throw an error
        ErrorHandler.throwError(100);
        return null;
    }
}
