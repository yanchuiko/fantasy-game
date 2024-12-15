package org.fantasygame.presenter.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Unit tests for GameFlowManager class.
 */
class GameFlowManagerTest {

    private InputHandler mockInputHandler; // Mock for handling player input
    private CommandHandler mockCommandHandler; // Mock for processing commands
    private GamePresenter mockGamePresenter; // Mock for game presentation
    private GameStateHandler mockGameStateHandler; // Mock for handling game state
    private GameFlowManager gameFlowManager; // The GameFlowManager instance to test

    /**
     * Sets up the test environment by initializing mocks and GameFlowManager.
     */
    @BeforeEach
    void setUp() {
        // Arrange: Initialize mocks for dependencies
        mockInputHandler = mock(InputHandler.class);
        mockCommandHandler = mock(CommandHandler.class);
        mockGamePresenter = mock(GamePresenter.class);
        mockGameStateHandler = mock(GameStateHandler.class);

        // Initialize GameFlowManager with mocked dependencies
        gameFlowManager = new GameFlowManager(mockInputHandler, mockCommandHandler, mockGamePresenter, mockGameStateHandler);
    }

    /**
     * Tests the game flow when the player wins the game.
     */
    @Test
    void testGameEndsWhenPlayerWins() {
        // Arrange: Simulate user input and win condition
        when(mockInputHandler.getInput()).thenReturn("command");
        when(mockCommandHandler.process(anyString())).thenReturn("Command executed");
        when(mockGameStateHandler.checkLevelTransition()).thenReturn(true);
        when(mockGameStateHandler.isGameWon()).thenReturn(true);

        // Act: Run the game loop
        gameFlowManager.start();

        // Assert: Verify interactions and flow
        verify(mockGamePresenter, atLeastOnce()).clearScreen(); // Clear screen at the start
        verify(mockGamePresenter, atLeastOnce()).renderGameStatus(); // Render initial game status
        verify(mockInputHandler).getInput(); // Retrieve user input
        verify(mockCommandHandler).process("command"); // Process user command
        verify(mockGamePresenter).showMessage("Command executed"); // Show feedback
        verify(mockGameStateHandler).checkLevelTransition(); // Check for level transition
        verify(mockGameStateHandler).handleLevelTransition(); // Handle level transition
        verify(mockGameStateHandler).isGameWon(); // Check for win condition
        verify(mockGamePresenter).updateGameWon(); // Display win message
        verify(mockGamePresenter, atLeastOnce()).renderGameStatus(); // Render final game status
    }

    /**
     * Tests the game flow when the player loses the game.
     */
    @Test
    void testGameEndsWhenPlayerLoses() {
        // Arrange: Simulate user input and game-over condition
        when(mockInputHandler.getInput()).thenReturn("command");
        when(mockCommandHandler.process(anyString())).thenReturn("Command executed");
        when(mockGameStateHandler.isGameOver()).thenReturn(true);

        // Act: Run the game loop
        gameFlowManager.start();

        // Assert: Verify interactions and flow
        verify(mockGamePresenter, atLeastOnce()).clearScreen(); // Clear screen at the start
        verify(mockGamePresenter, atLeastOnce()).renderGameStatus(); // Render initial game status
        verify(mockInputHandler).getInput(); // Retrieve user input
        verify(mockCommandHandler).process("command"); // Process user command
        verify(mockGamePresenter).showMessage("Command executed"); // Show feedback
        verify(mockGameStateHandler).isGameOver(); // Check for game-over condition
        verify(mockGamePresenter).updateGameOver(); // Display game-over message
        verify(mockGamePresenter, atLeastOnce()).renderGameStatus(); // Render final game status
        verify(mockGamePresenter, atLeastOnce()).clearScreen(); // Clear screen before game over
    }
}
