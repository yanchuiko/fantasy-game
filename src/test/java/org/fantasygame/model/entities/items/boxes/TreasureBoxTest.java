package org.fantasygame.model.entities.items.boxes;

import org.fantasygame.model.entities.content.items.boxes.TreasureBox;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.mockito.Mockito.*;

class TreasureBoxTest {

    private TreasureBox treasureBox;
    private Player player;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize Player
        player = new Player(4, 1, 100);
    }

    /**
     * Tests that using the TreasureBox increases the player's power points by 25
     * when the mock Random is set to return the lowest value.
     */
    @Test
    void testUseAddsCorrectPowerPoints() {
        // Arrange: Mock Random to always return 25 power points
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(3)).thenReturn(0);

        // Arrange: Inject mock Random into TreasureBox
        treasureBox = new TreasureBox(mockRandom);

        // Act: Use the Treasure Box
        treasureBox.use(player);

        // Assert: Verify player's power points increased by 25
        Assertions.assertEquals(125, player.getPowerPoints(), "Player's power points should increase by 25");
    }

    /**
     * Tests that the chosen power points from TreasureBox match the expected value
     * when the mock Random is set to return the middle value (50).
     */
    @Test
    void testChosenPowerPointsAreCorrect() {
        // Arrange: Mock Random to always return 50 power points
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(3)).thenReturn(1);

        // Arrange: Inject mock Random into TreasureBox
        treasureBox = new TreasureBox(mockRandom);

        // Act: Use the Treasure Box
        treasureBox.use(player);

        // Assert: Verify getChosenPowerPoints() matches the selected power points
        Assertions.assertEquals(50, treasureBox.getChosenPowerPoints(), "getChosenPowerPoints() should return 50");
    }

    /**
     * Tests that using the TreasureBox with a real Random instance generates a valid power point
     * value of 25, 50, or 75.
     */
    @Test
    void testUseWithRealRandom() {
        // Arrange: Use real Random instance
        treasureBox = new TreasureBox(new Random());

        // Act: Use the Treasure Box
        treasureBox.use(player);

        // Assert: Verify the chosen power points are valid (25, 50, or 75)
        int chosenPowerPoints = treasureBox.getChosenPowerPoints();
        Assertions.assertTrue(
                chosenPowerPoints == 25 || chosenPowerPoints == 50 || chosenPowerPoints == 75,
                "Chosen power points should be 25, 50, or 75"
        );
    }
}
