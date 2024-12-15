package org.fantasygame.view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameViewTest {

    /**
     * Tests that the rendered map correctly marks the player's position.
     */
    @Test
    void testRenderMapPlayerPosition() {
        // Arrange: Set up the GameView, map grid, and player's position
        GameView gameView = new GameView();

        String[][] mapGrid = {
                {"W", "E", "W"},
                {"-", "-", "-"},
                {"W", "X", "W"}
        };

        int playerRow = 1;
        int playerCol = 1;

        // Act: Render the map with the player's position
        String renderedMap = gameView.renderMap(mapGrid, playerRow, playerCol);

        String expectedMap = """
                W E W\s
                - P -\s
                W X W""";

        // Assert: Verify the rendered map matches the expected output
        assertEquals(expectedMap, renderedMap, "The rendered map should correctly mark player's position.");
    }
}
