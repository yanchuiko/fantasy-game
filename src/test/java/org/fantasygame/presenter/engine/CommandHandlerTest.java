package org.fantasygame.presenter.engine;

import org.fantasygame.presenter.commands.Command;
import org.fantasygame.presenter.commands.CommandFactory;
import org.fantasygame.model.services.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommandHandlerTest {

    private CommandHandler commandHandler;
    private PlayerService mockPlayerService;

    /**
     * Sets up the test environment by initializing a mocked PlayerService and CommandHandler.
     */
    @BeforeEach
    void setUp() {
        // Arrange: Mock PlayerService and initialize CommandHandler
        mockPlayerService = mock(PlayerService.class);
        commandHandler = new CommandHandler(mockPlayerService);
    }

    /**
     * Tests processing a valid command using the CommandHandler.
     * Ensures the correct output is returned when a valid command is executed.
     */
    @Test
    void testProcessValidCommand() {
        try (MockedStatic<CommandFactory> mockedCommandFactory = mockStatic(CommandFactory.class)) {
            // Arrange: Mock CommandFactory and command execution
            Command mockCommand = mock(Command.class);
            String userInput = "move forward";
            String expectedOutput = "You move forward";

            mockedCommandFactory.when(() -> CommandFactory.createCommand(userInput)).thenReturn(mockCommand);
            when(mockCommand.execute(mockPlayerService)).thenReturn(expectedOutput);

            // Act: Process the user input through CommandHandler
            String result = commandHandler.process(userInput);

            // Assert: Verify the output and execution behavior
            assertEquals(expectedOutput, result, "The command should return the correct output.");
            verify(mockCommand).execute(mockPlayerService);
        }
    }

    /**
     * Tests processing an invalid command using the CommandHandler.
     * Ensures an appropriate error message is returned for invalid commands.
     */
    @Test
    void testProcessInvalidCommand() {
        try (MockedStatic<CommandFactory> mockedCommandFactory = mockStatic(CommandFactory.class)) {
            // Arrange: Mock CommandFactory to throw an exception for invalid command
            String userInput = "test test test";
            String expectedError = "Invalid command";

            mockedCommandFactory.when(() -> CommandFactory.createCommand(userInput))
                    .thenThrow(new IllegalArgumentException(expectedError));

            // Act: Process the invalid input through CommandHandler
            String result = commandHandler.process(userInput);

            // Assert: Verify the error message is returned
            assertEquals(expectedError, result, "The processor should return the error message for invalid commands.");
        }
    }
}
