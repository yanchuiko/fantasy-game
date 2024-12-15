package org.fantasygame.presenter.engine;

import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.LevelService;
import org.fantasygame.model.services.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GameStateHandler class.
 */
class GameStateHandlerTest {

    private GameStateHandler gameStateHandler;
    private Player mockPlayer;
    private Level mockLevel;
    private PlayerService mockPlayerService;
    private LevelService mockLevelService;
    private GamePresenter mockGamePresenter;

    /**
     * Sets up the test environment by initializing mocks and GameStateHandler.
     */
    @BeforeEach
    void setUp() {
        // Arrange: Initialize mocks
        mockPlayer = mock(Player.class);
        mockLevel = mock(Level.class);
        mockPlayerService = mock(PlayerService.class);
        mockLevelService = mock(LevelService.class);
        mockGamePresenter = mock(GamePresenter.class);

        // Initialize GameStateHandler with mocks
        gameStateHandler = new GameStateHandler(mockPlayer, mockLevel, mockPlayerService, mockLevelService, mockGamePresenter);
    }

    /**
     * Tests if the game is correctly identified as over when player's power points reach zero.
     */
    @Test
    void testGameOverWhenPowerPointsZero() {
        // Arrange: Simulate player's power points as zero
        when(mockPlayer.getPowerPoints()).thenReturn(0);

        // Act: Check if the game is over
        boolean result = gameStateHandler.isGameOver();

        // Assert: Verify the game is over
        assertTrue(result, "Game should be over if power points are zero");
    }

    /**
     * Tests if the game is correctly identified as not over when player's power points are greater than zero.
     */
    @Test
    void testGameOverWhenPowerPointsGreaterThanZero() {
        // Arrange: Simulate player's power points as greater than zero
        when(mockPlayer.getPowerPoints()).thenReturn(100);

        // Act: Check if the game is over
        boolean result = gameStateHandler.isGameOver();

        // Assert: Verify the game is not over
        assertFalse(result, "Game should not be over if power points are greater than zero");
    }

    /**
     * Tests if the game is won when the player reaches the final level's exit.
     */
    @Test
    void testGameWonWhenPlayerAtFinalExit() {
        // Arrange: Simulate player at the final level's exit
        when(mockLevel.getLevelNumber()).thenReturn(3);
        when(mockLevel.getExit()).thenReturn(new int[]{2, 2});
        when(mockPlayer.getRow()).thenReturn(2);
        when(mockPlayer.getCol()).thenReturn(2);

        // Act: Check if the game is won
        boolean result = gameStateHandler.isGameWon();

        // Assert: Verify the game is won
        assertTrue(result, "Game should be won if player is at the final level's exit");
    }

    /**
     * Tests if the game is not won when the player is not at the final level's exit.
     */
    @Test
    void testGameWonWhenPlayerNotAtFinalExit() {
        // Arrange: Simulate player not at the final level's exit
        when(mockLevel.getLevelNumber()).thenReturn(3);
        when(mockLevel.getExit()).thenReturn(new int[]{2, 2});
        when(mockPlayer.getRow()).thenReturn(1);
        when(mockPlayer.getCol()).thenReturn(1);

        // Act: Check if the game is won
        boolean result = gameStateHandler.isGameWon();

        // Assert: Verify the game is not won
        assertFalse(result, "Game should not be won if player is not at the final level's exit");
    }

    /**
     * Tests if a level transition is correctly triggered when the player is in an exit room.
     */
    @Test
    void testCheckLevelTransitionWhenInExitRoom() {
        // Arrange: Simulate player in an exit room
        Room mockRoom = mock(Room.class);
        when(mockRoom.getType()).thenReturn("exit");
        when(mockLevel.getRoom(anyInt(), anyInt())).thenReturn(mockRoom);

        // Act: Check level transition
        boolean result = gameStateHandler.checkLevelTransition();

        // Assert: Verify level transition is triggered
        assertTrue(result, "Level transition should occur if player is in an exit room");
    }

    /**
     * Tests if a level transition is not triggered when the player is not in an exit room.
     */
    @Test
    void testCheckLevelTransitionWhenNotInExitRoom() {
        // Arrange: Simulate player not in an exit room
        Room mockRoom = mock(Room.class);
        when(mockRoom.getType()).thenReturn("normal");
        when(mockLevel.getRoom(anyInt(), anyInt())).thenReturn(mockRoom);

        // Act: Check level transition
        boolean result = gameStateHandler.checkLevelTransition();

        // Assert: Verify level transition is not triggered
        assertFalse(result, "Level transition should not occur if player is not in an exit room");
    }

    /**
     * Tests if the game is correctly handled as won when the player reaches the final level's exit.
     */
    @Test
    void testHandleLevelTransitionWinsGame() {
        // Arrange: Simulate player winning the game
        when(mockLevel.getLevelNumber()).thenReturn(3);
        when(mockLevel.getExit()).thenReturn(new int[]{2, 2});
        when(mockPlayer.getRow()).thenReturn(2);
        when(mockPlayer.getCol()).thenReturn(2);

        // Act: Handle level transition
        gameStateHandler.handleLevelTransition();

        // Assert: Verify the game presenter shows the win message
        verify(mockGamePresenter).updateGameWon();
        verifyNoMoreInteractions(mockLevelService, mockPlayerService);
    }

    /**
     * Tests if transitioning to the next level is handled correctly when the player is in an exit room.
     */
    @Test
    void testHandleLevelTransitionToNextLevel() {
        // Arrange: Simulate transitioning to the next level
        when(mockLevel.getLevelNumber()).thenReturn(1);
        Level nextLevel = mock(Level.class);
        when(mockLevelService.initializeLevel(eq(2), anyString())).thenReturn(nextLevel);

        // Act: Handle level transition
        gameStateHandler.handleLevelTransition();

        // Assert: Verify the level transition logic
        verify(mockLevelService).initializeLevel(2, "src/main/resources/map.csv");
        verify(mockLevelService).transitionToNextLevel(nextLevel, mockPlayer);
        verify(mockPlayerService).updateLevel(nextLevel);
        verify(mockGamePresenter).updateFoundExit();
    }
}
