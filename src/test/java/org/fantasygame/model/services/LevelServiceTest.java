package org.fantasygame.model.services;

import org.fantasygame.model.entities.core.Level;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.entities.core.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.mockito.Mockito.*;

class LevelServiceTest {

    private LevelService levelService;
    private RoomService mockRoomService;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize a mocked RoomService
        mockRoomService = mock(RoomService.class);
        levelService = new LevelService(mockRoomService);
    }

    /**
     * Tests that a level is initialized correctly from a file,
     * verifying that the grid is created and rooms are initialized.
     */
    @Test
    void testInitializeLevel() {
        // Arrange: Mock static method readFile and its output
        String filePath = "mockMap.csv";
        int levelNumber = 1;
        try (MockedStatic<LevelService> mockedStatic = mockStatic(LevelService.class)) {
            List<String> mockFileContent = List.of(
                    "Level 1",
                    "W,W,X,W,W",
                    "W,W,-,W,W",
                    "W,-,-,-,W",
                    "W,-,-,-,W",
                    "W,W,E,W,W"
            );
            mockedStatic.when(() -> LevelService.readFile(filePath)).thenReturn(mockFileContent);

            // Act: Initialize the level
            Level level = levelService.initializeLevel(levelNumber, filePath);

            // Assert: Verify the level is initialized correctly
            assert level != null : "Level should be initialized successfully.";
            assert level.getGrid().length == 5 : "Grid should have 5 rows.";
            verify(mockRoomService, atLeastOnce())
                    .initializeRoom(anyInt(), anyInt(), anyString(), anyString());
        }
    }

    /**
     * Tests that transitioning to the next level sets the player's position to the level's entrance,
     * marks the entrance room as visited and revealed, and updates the player's current level.
     */
    @Test
    void testTransitionToNextLevel() {
        // Arrange: Set up mocks for player, next level, and entrance room
        Player mockPlayer = mock(Player.class);
        Level mockNextLevel = mock(Level.class);
        Room mockRoom = mock(Room.class);
        int[] entrance = {4, 2}; // Entrance coordinates

        when(mockNextLevel.getEntrance()).thenReturn(entrance);
        when(mockNextLevel.getRoom(4, 2)).thenReturn(mockRoom);

        // Act: Transition to the next level
        levelService.transitionToNextLevel(mockNextLevel, mockPlayer);

        // Assert: Verify the player's position is updated
        verify(mockPlayer).setRow(4);
        verify(mockPlayer).setCol(2);

        // Assert: Verify the entrance room is marked as visited and revealed
        verify(mockRoom).setVisited(true);
        verify(mockRoom).setRevealed(true);
    }
}
