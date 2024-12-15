package org.fantasygame.presenter.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryAliasesTest {

    /**
     * Tests aliases for the "move forward" command.
     */
    @Test
    void testAliasMoveForward() {
        // Arrange
        String[] aliases = {"move forward", "go forward", "forward", "w"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias);

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(MovementCommand.class, command, "Command should be a MovementCommand for alias: " + alias);
            assertEquals("forward", ((MovementCommand) command).getDirection(), "Direction should be 'forward'.");
        }
    }

    /**
     * Tests aliases for the "move back" command.
     */
    @Test
    void testAliasMoveBack() {
        // Arrange
        String[] aliases = {"move back", "go back", "back", "s"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias);

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(MovementCommand.class, command, "Command should be a MovementCommand for alias: " + alias);
            assertEquals("back", ((MovementCommand) command).getDirection(), "Direction should be 'back'.");
        }
    }

    /**
     * Tests aliases for the "move left" command.
     */
    @Test
    void testAliasMoveLeft() {
        // Arrange
        String[] aliases = {"move left", "go left", "left", "a"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias);

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(MovementCommand.class, command, "Command should be a MovementCommand for alias: " + alias);
            assertEquals("left", ((MovementCommand) command).getDirection(), "Direction should be 'left'.");
        }
    }

    /**
     * Tests aliases for the "move right" command.
     */
    @Test
    void testAliasMoveRight() {
        // Arrange
        String[] aliases = {"move right", "go right", "right", "d"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias);

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(MovementCommand.class, command, "Command should be a MovementCommand for alias: " + alias);
            assertEquals("right", ((MovementCommand) command).getDirection(), "Direction should be 'right'.");
        }
    }

    /**
     * Tests aliases for the "look around" command.
     */
    @Test
    void testAliasLookAround() {
        // Arrange
        String[] aliases = {"look around", "search", "l"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias);

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(LookAroundCommand.class, command, "Command should be a LookAroundCommand for alias: " + alias);
        }
    }

    /**
     * Tests aliases for the "pick up" command.
     */
    @Test
    void testAliasPickUp() {
        // Arrange
        String[] aliases = {"pick up", "take", "p"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias + " cake");

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(PickUpCommand.class, command, "Command should be a PickUpCommand for alias: " + alias);
            assertEquals("cake", ((PickUpCommand) command).getItemName(), "Item name should be 'cake'.");
        }
    }

    /**
     * Tests aliases for the "eat" command.
     */
    @Test
    void testAliasEat() {
        // Arrange
        String[] aliases = {"eat", "e"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias + " cake");

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(EatCommand.class, command, "Command should be an EatCommand for alias: " + alias);
            assertEquals("cake", ((EatCommand) command).getFoodName(), "Food name should be 'cake'.");
        }
    }

    /**
     * Tests aliases for the "drink" command.
     */
    @Test
    void testAliasDrink() {
        // Arrange
        String[] aliases = {"drink", "dr"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias + " potion");

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(DrinkCommand.class, command, "Command should be a DrinkCommand for alias: " + alias);
            assertEquals("potion", ((DrinkCommand) command).getPotionName(), "Potion name should be 'potion'.");
        }
    }

    /**
     * Tests aliases for the "use" command.
     */
    @Test
    void testAliasUse() {
        // Arrange
        String[] aliases = {"use", "activate"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias + " teleportation spell");

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(UseCommand.class, command, "Command should be a UseCommand for alias: " + alias);
            assertEquals("teleportation spell", ((UseCommand) command).getSpellName(), "Spell name should be 'teleportation spell'.");
        }
    }

    /**
     * Tests aliases for the "drop" command.
     */
    @Test
    void testAliasDrop() {
        // Arrange
        String[] aliases = {"drop", "leave"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias + " hammer");

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(DropCommand.class, command, "Command should be a DropCommand for alias: " + alias);
            assertEquals("hammer", ((DropCommand) command).getItemName(), "Item name should be 'hammer'.");
        }
    }

    /**
     * Tests aliases for the "open" command.
     */
    @Test
    void testAliasOpen() {
        // Arrange
        String[] aliases = {"open", "unlock"};

        for (String alias : aliases) {
            // Act
            Command command = CommandFactory.createCommand(alias + " pandora box");

            // Assert
            assertNotNull(command, "Command should not be null for alias: " + alias);
            assertInstanceOf(OpenCommand.class, command, "Command should be an OpenCommand for alias: " + alias);
            assertEquals("pandora box", ((OpenCommand) command).getBoxName(), "Box name should be 'pandora box'.");
        }
    }
}
