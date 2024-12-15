package org.fantasygame.model.entities.items.potions;

import org.fantasygame.model.entities.content.items.potions.LuckyPotion;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.mockito.Mockito.*;

class LuckyPotionTest {

    private LuckyPotion luckyPotion;
    private Player player;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize Player and mock Random
        player = new Player(4, 1, 100);
        mockRandom = mock(Random.class);

        // Arrange: Inject mocked Random into LuckyPotion
        luckyPotion = new LuckyPotion(mockRandom);
    }

    /**
     * Tests that using the lucky potion increases the player's power points by 50
     * when the player is "lucky."
     */
    @Test
    void testUsePlayerWasLucky() {
        // Arrange: Simulate a "lucky" outcome
        when(mockRandom.nextBoolean()).thenReturn(true);

        // Act: Use the lucky potion on the player
        luckyPotion.use(player);

        // Assert: Verify the player's power points increased by 50
        Assertions.assertEquals(150, player.getPowerPoints(), "Player's power points should increase by 50");
    }

    /**
     * Tests that using the lucky potion decreases the player's power points by 50
     * when the player is "unlucky."
     */
    @Test
    void testUsePlayerWasUnlucky() {
        // Arrange: Simulate an "unlucky" outcome
        when(mockRandom.nextBoolean()).thenReturn(false);

        // Act: Use the lucky potion on the player
        luckyPotion.use(player);

        // Assert: Verify the player's power points decreased by 50
        Assertions.assertEquals(50, player.getPowerPoints(), "Player's power points should decrease by 50");
    }

    /**
     * Tests that using the lucky potion multiple times alternates the player's power points
     * based on successive lucky and unlucky outcomes.
     */
    @Test
    void testUseMultipleTimes() {
        // Arrange: Alternate between lucky and unlucky outcomes
        when(mockRandom.nextBoolean()).thenReturn(true, false, true);

        // Act: Use the lucky potion three times
        luckyPotion.use(player); // First use (lucky)
        luckyPotion.use(player); // Second use (unlucky)
        luckyPotion.use(player); // Third use (lucky)

        // Assert: Verify the player's power points reflect cumulative effects
        Assertions.assertEquals(150, player.getPowerPoints(), "Player's power points should reflect cumulative effects of luck and unluck");
    }
}
