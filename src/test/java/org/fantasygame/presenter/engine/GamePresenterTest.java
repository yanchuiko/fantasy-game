package org.fantasygame.presenter.engine;

import org.fantasygame.model.entities.core.Inventory;
import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.fantasygame.model.services.PlayerService;
import org.fantasygame.view.GameView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class GamePresenterTest {

    private GameView mockGameView;
    private Player mockPlayer;
    private Level mockLevel;
    private PlayerService mockPlayerService;
    private Inventory mockInventory;
    private GamePresenter gamePresenter;

    /**
     * Sets up the test environment by mocking dependencies and initializing the GamePresenter.
     */
    @BeforeEach
    void setUp() {
        // Arrange: Mock dependencies
        mockGameView = mock(GameView.class);
        mockPlayer = mock(Player.class);
        mockLevel = mock(Level.class);
        mockPlayerService = mock(PlayerService.class);
        mockInventory = mock(Inventory.class);

        // Link mocked inventory to mocked player
        when(mockPlayer.getInventory()).thenReturn(mockInventory);

        // Mock the level's grid
        Room[][] mockGrid = new Room[5][5]; // Example 5x5 grid
        for (int i = 0; i < mockGrid.length; i++) {
            for (int j = 0; j < mockGrid[i].length; j++) {
                mockGrid[i][j] = mock(Room.class);
                when(mockGrid[i][j].isRevealed()).thenReturn(true);
                when(mockGrid[i][j].getType()).thenReturn("default");
            }
        }
        when(mockLevel.getGrid()).thenReturn(mockGrid);

        // Initialize GamePresenter with mocked dependencies
        gamePresenter = new GamePresenter(mockGameView, mockPlayer, mockLevel, mockPlayerService);
    }

    /**
     * Tests the `renderGameStatus` method of `GamePresenter`.
     * Verifies that the game status is rendered correctly with the expected values.
     */
    @Test
    void testRenderGameStatus() {
        // Arrange: Mock player, level, and inventory details
        when(mockLevel.getLevelNumber()).thenReturn(1);
        when(mockPlayer.getRow()).thenReturn(2);
        when(mockPlayer.getCol()).thenReturn(3);
        when(mockPlayer.getPowerPoints()).thenReturn(100);
        when(mockPlayerService.getRoomDescription()).thenReturn("This is a test room.");
        when(mockInventory.getFormattedInventory()).thenReturn("Inventory: Cake, Hammer");

        // Mock the map rendering
        when(mockGameView.renderMap(any(String[][].class), eq(2), eq(3))).thenReturn("Rendered Map");

        // Act: Call the method to render the game status
        gamePresenter.renderGameStatus();

        // Assert: Verify that the GameView's `displayGameStatus` method is called with correct arguments
        verify(mockGameView).displayGameStatus(
                eq("Level 1, Room (3, 4)"), // Player position (1-based index)
                eq("This is a test room."), // Room description
                eq("Rendered Map"), // Rendered map
                eq(100), // Player's power points
                eq("Inventory: Cake, Hammer") // Formatted inventory
        );
    }
}
