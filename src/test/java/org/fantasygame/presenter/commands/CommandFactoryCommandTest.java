package org.fantasygame.presenter.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryCommandTest {

    /**
     * Tests the creation of a "move forward" command.
     */
    @Test
    void testCreateCommandMoveForward() {
        // Arrange
        String commandInput = "move forward";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(MovementCommand.class, command, "Command should be a MovementCommand.");
        assertEquals("forward", ((MovementCommand) command).getDirection(), "Direction should be 'forward'.");
    }

    /**
     * Tests the creation of a "move back" command.
     */
    @Test
    void testCreateCommandMoveBack() {
        // Arrange
        String commandInput = "move back";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(MovementCommand.class, command, "Command should be a MovementCommand.");
        assertEquals("back", ((MovementCommand) command).getDirection(), "Direction should be 'back'.");
    }

    /**
     * Tests the creation of a "move left" command.
     */
    @Test
    void testCreateCommandMoveLeft() {
        // Arrange
        String commandInput = "move left";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(MovementCommand.class, command, "Command should be a MovementCommand.");
        assertEquals("left", ((MovementCommand) command).getDirection(), "Direction should be 'left'.");
    }

    /**
     * Tests the creation of a "move right" command.
     */
    @Test
    void testCreateCommandMoveRight() {
        // Arrange
        String commandInput = "move right";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(MovementCommand.class, command, "Command should be a MovementCommand.");
        assertEquals("right", ((MovementCommand) command).getDirection(), "Direction should be 'right'.");
    }

    /**
     * Tests the creation of a "look around" command.
     */
    @Test
    void testCreateCommandLookAround() {
        // Arrange
        String commandInput = "look around";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(LookAroundCommand.class, command, "Command should be a LookAroundCommand.");
    }

    /**
     * Tests the creation of a "pick up" command with an item name.
     */
    @Test
    void testCreateCommandPickUp() {
        // Arrange
        String commandInput = "pick up cake";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(PickUpCommand.class, command, "Command should be a PickUpCommand.");
        assertEquals("cake", ((PickUpCommand) command).getItemName(), "Item name should be 'cake'.");
    }

    /**
     * Tests the creation of an "eat" command with a food name.
     */
    @Test
    void testCreateCommandEat() {
        // Arrange
        String commandInput = "eat salad";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(EatCommand.class, command, "Command should be an EatCommand.");
        assertEquals("salad", ((EatCommand) command).getFoodName(), "Food name should be 'salad'.");
    }

    /**
     * Tests the creation of a "drink" command with a potion name.
     */
    @Test
    void testCreateCommandDrink() {
        // Arrange
        String commandInput = "drink potion";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(DrinkCommand.class, command, "Command should be a DrinkCommand.");
        assertEquals("potion", ((DrinkCommand) command).getPotionName(), "Potion name should be 'potion'.");
    }

    /**
     * Tests the creation of a "use" command with a spell name.
     */
    @Test
    void testCreateCommandUse() {
        // Arrange
        String commandInput = "use freeze spell";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(UseCommand.class, command, "Command should be a UseCommand.");
        assertEquals("freeze spell", ((UseCommand) command).getSpellName(), "Spell name should be 'freeze spell'.");
    }

    /**
     * Tests the creation of a "drop" command with an item name.
     */
    @Test
    void testCreateCommandDrop() {
        // Arrange
        String commandInput = "drop potion";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(DropCommand.class, command, "Command should be a DropCommand.");
        assertEquals("potion", ((DropCommand) command).getItemName(), "Item name should be 'potion'.");
    }

    /**
     * Tests the creation of an "open" command with a box name.
     */
    @Test
    void testCreateCommandOpen() {
        // Arrange
        String commandInput = "open pandora box";

        // Act
        Command command = CommandFactory.createCommand(commandInput);

        // Assert
        assertNotNull(command, "Command should not be null for valid input.");
        assertInstanceOf(OpenCommand.class, command, "Command should be an OpenCommand.");
        assertEquals("pandora box", ((OpenCommand) command).getBoxName(), "Box name should be 'pandora box'.");
    }
}
